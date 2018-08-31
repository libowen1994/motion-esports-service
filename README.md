# Motion Service


Motion Service by Spring Boot



# 開發準備

   Notes:
   1. `./gradlew` 指令，在 Windows 環境中，請自行置換成 `gradlew.bat`

## 開發環境準備
參與開發人員的工作機，需要安裝下列軟體：

  - Git
  - JDK 8
  - Docker for Mac or Windows
  
## 下載代碼(Clone code)
  
```
git clone git@bitbucket.org:evatar/motion-oauth-service.git 
```  

## 建立 IDE project

假設目前在專案所在目錄，根據所使用的 IDE 選擇


```
./gradlew idea
```

或者 
  
```
./gradlew eclipse
```

## 複製 docker-compose.override.yaml 檔案

複製 docker-compose.override.yaml.example 為 docker-compose.override.yaml，
此檔案用來客製化 docker-compose.yaml 中的設定以適應不同開發人員的環境。
例如：某開發人員有些 port 已經被佔用，或者希望同時跑其它服務容器。

  
# 一般操作(根據需要操作，需要啟動本機測試環境)

## 啟動本機測試環境
```
docker-compose up -d
```

測試環境提供 MySQL, Kafka, Redis 等支援性質的服務。

## 關閉本機測試環境
不再需要測試，可以使用下列指令清除：

```
docker-compose down
```

Note: 底下的操作都需要本機測試環境啟用狀態下。

## 建構(包含單元以及整合測試)

```
docker-compose exec app gradlew clean build
```

## 建構(不包含整合測試)

```
docker-compose exec app gradle clean build -x integrationTest
```

## 執行單元測試
```
docker-compose exec app gradle clean test
```

## 執行整合測試
```
docker-compose exec app gradle clean integrationTest
```

執行下列指令，啟動 web應用:
```
docker-compose exec app gradle bootRun
```

開啟瀏覽器，使用下列連結測試：

* 前台網址： [http://localhost:8080/](http://localhost:8080/)


## 檢視服務日誌

使用下列指令可以 follow 所有服務顯示的日誌內容。
```
docker-compose logs -f
```
如果只需要看單一服務的日誌，後面加上服務容器的名稱，例如：
```
docker-compose logs -f maindb
```
可以檢視 MySQL 資料庫顯示的控制台輸出。

不繼續 follow, 只需要 CTRL+C 就可以。


# 組態設定

### 环境变量
name           | description
---            | ---
SPRING_PROFILES_ACTIVE| 生产环境: prod, 测试环境: test
MOTION_WALLET_SERVICE_ADDR | motion-wallet-service 地址，不用http 
KAFKA_HOSTS | kafka brokers 多个broker用,分开
REDIS_HOST     | redis主机
REDIS_PASSWORD | redis密码
MYSQL_HOST     | user-portal mysql主机
DB_NAME        | user-portal db名称
DB_USER        | user-portal db账号
DB_PASSWORD    | user-portal db账号密码

