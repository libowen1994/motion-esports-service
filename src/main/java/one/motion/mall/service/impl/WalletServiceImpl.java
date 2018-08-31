package one.motion.mall.service.impl;

import com.alibaba.fastjson.JSONObject;
import io.grpc.Channel;
import net.devh.springboot.autoconfigure.grpc.client.GrpcClient;
import one.motion.mall.service.IWalletService;
import one.motion.proto.wallet.model.ExpendMsgProto;
import one.motion.proto.wallet.model.ExpendResultProto;
import one.motion.proto.wallet.service.MtnServiceGrpc;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;

@Service
public class WalletServiceImpl implements IWalletService {

    private final Logger logger = LoggerFactory.getLogger(getClass());

    private final RestTemplate restTemplate;

    @Value("${SHB_MERCHANT_NUMBER:}")
    private String SHB_MERCHANT_NUMBER;

    public WalletServiceImpl(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @GrpcClient("wallet-service")
    private Channel walletServiceChannel;

    @Value("${MOTION_WALLET_SERVICE_ADDR:}")
    private String walletAddress;

    @Override
    public BigDecimal getMtnValue(BigDecimal amount, String currency) {
        try {
            String url = "http://" + walletAddress + ":8080/oapi/v1/wallet/exchange?amount=" + amount + "&currency" + currency;
            JSONObject result = restTemplate.getForObject(url, JSONObject.class);
            if (result == null) {
                return BigDecimal.ZERO;
            }
            String code = result.getString("code");
            if (!StringUtils.equals(code, "200")) {
                return BigDecimal.ZERO;
            }
            String mtnStr = result.getJSONObject("data").getString("mtn");
            try {
                return new BigDecimal(mtnStr);
            } catch (Exception e) {
                logger.error(e.getMessage());
                return BigDecimal.ZERO;
            }
        } catch (Exception e) {
            logger.error(e.getMessage());
            return BigDecimal.ZERO;
        }
    }

    @Override
    public JSONObject expendMTN(BigDecimal amount, String orderId, Long userId) {
        MtnServiceGrpc.MtnServiceBlockingStub stub = MtnServiceGrpc.newBlockingStub(walletServiceChannel);
        ExpendMsgProto request = ExpendMsgProto.newBuilder()
                .setMtn(amount.toString())
                .setType(1)
                .setOrderId(orderId)
                .setUserId(userId)
                .build();
        ExpendResultProto result = stub.saveMtnExpend(request);
        String codeName = result.getResultCode().name();
        int code = result.getResultCode().getNumber();
        String message = result.getResultMsg();
        logger.info("Send transaction result: {}:{}, {}", code, codeName, message);
        JSONObject json = new JSONObject();
        json.put("code", code);
        json.put("message", message);
        return json;
    }
}
