# Observability & Distributed Tracing [![Build Status](https://github.com/biandratti/agents/actions/workflows/ci.yml/badge.svg?branch=master)](https://github.com/biandratti/agents/actions/workflows/ci.yml) [![codecov](https://codecov.io/gh/biandratti/agents/branch/master/graph/badge.svg?token=MMS4N0N8KQ)](https://codecov.io/gh/biandratti/agents)

Monitoring as an example using Kamon and OpenTelemetry agents in applications built on Play framework and ZIO Http.
The main idea of this example is to reproduce the traceability concept for different scala frameworks and different telemetry options.

#### Run apps with agent:
```
sbt app1/docker:publishLocal
sbt app2/docker:publishLocal
sbt app3/docker:publishLocal
sbt app4/docker:publishLocal
sbt app5/docker:publishLocal
```
or build all the apps images 
```
./build-apps.sh
```

## Kamon (app1, app2) test cross apps

![image](https://user-images.githubusercontent.com/72261652/233176385-0f874b19-99e4-4232-a5c0-a21362c5df9f.png)
```
docker/kammon/docker-compose up
```
#### Getting trace_id: app1 -> app2
```
curl -i  --header "context-id: mycontextid" localhost:9001/api/v1/trace
```
#### Prometheus:

http://localhost:9091/metrics

http://localhost:9092/metrics

## OpenTelemetry (app3, app4, app5) test cross apps

![image](https://user-images.githubusercontent.com/72261652/233178921-9a1bc156-f133-4702-9698-14b8453354cd.png)
```
docker/opentelemetry/docker-compose up
```
Getting trace_id: app3 -> (app4 || app5)
```
curl -i  --header "context-id: mycontextid" localhost:9003/api/v1/trace
curl -i  --header "context-id: error" localhost:9003/api/v1/trace
```

#### Prometheus:

http://localhost:9093

http://localhost:9094

http://localhost:9095

#### jaegertracing
http://localhost:16686

#### Performance tests:
```
sbt -Dusers=1 -Dramp=1 -Dport=9000 Gatling/test [For kamon example]
sbt -Dusers=1 -Dramp=1 -Dport=9003 Gatling/test [For opentelemetry example]
```