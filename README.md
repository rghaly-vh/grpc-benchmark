# Build Order
1. grpc-interface
2. grpc-service
3. rest-service-spi
4. rest-service
5. benchmark-client

# To test GRPC directly
install grpcurl (https://formulae.brew.sh/formula/grpcui)

## Command:
grpcurl --plaintext -d '{"milliseconds": 1000}' localhost:9090 com.vibrenthealth.benchmark.grpc.service.GrpcBenchmarkService/DoSomething

#To test REST directly]
install Postman (https://formulae.brew.sh/cask/postman)

## Config:
POST <host>:<port>/doSomething
### Payload:
```
{
    "milliseconds" : 1000
}
```

# To invoke the benchmark-client
* [needs Postman](README.md:14)

## Payload:
Method: POST
```
{
"serviceType": "SPRING",
"testId": "5000_LOOP",
"loopCount": 5000,
"repeats": 15,
"sleepMills": 5,
"primer": {
    "sleepMills": 10,
    "iterations": 20
    }
}
```

## Payload Description:
* `serviceType`: what is the target service for the run. One of ‘SPRING’, ‘REST’, or ‘GRPC’
* `testId`: a logical name for the test. This will be used to compare results across the different service types for each scenario.
* `loopCount`: this is to emulate the implementation of a client calling a service in a loop and it is also the distinction factor (along with serviceType) between the different test scenarios.
* `repeats`: number of times to repeat the particular test scenario. This is aimed at increasing the sample population for statistical significance.
* `sleepMills`: the amount of time the service needs to go to sleep (Thread.sleep() argument). This is the ‘workload’.
* `primer`: values used to ‘prime’ the target service. The intent is to eliminate any initialization overhead that may be incurred due to firstime invocation (object allocation time, establishing connections, etc).
  * `sleepMills`: workload value for a primer call (10 milliseconds is used for all test scenarios)
  * `iterations`: how many times to prime the target service (20 iterations is used for all test scenarios)
