# Comprehensive Observability and Distributed Tracing with Kamon and OpenTelemetry [![Build Status](https://github.com/biandratti/agents/actions/workflows/ci.yml/badge.svg?branch=master)](https://github.com/biandratti/agents/actions/workflows/ci.yml) [![codecov](https://codecov.io/gh/biandratti/agents/branch/master/graph/badge.svg?token=MMS4N0N8KQ)](https://codecov.io/gh/biandratti/agents)

This repository demonstrates how to implement monitoring and tracing in applications built with the Play Framework and ZIO Http using Kamon and OpenTelemetry agents. 
The main goal is to showcase the concept of traceability across different Scala frameworks and telemetry options.

#### Build all the apps images 
```
./docker/build-apps.sh
```

---
### Example 1: Kamon agent

* app1 and app2 are Play Framework applications.
* Both apps use Kamon for monitoring.
* Kamon sends tracing data to Jaeger.
* Kamon also sends metrics to Prometheus.
* Prometheus: 
  * http://localhost:9091/metrics
  * http://localhost:9092/metrics
* jaeger: http://localhost:16686

```
cd docker/kammon && docker compose up
```
#### Getting trace_id: app1 -> app2
In the example, the user sends a request to app1 and the same trace id is sent and distributed to app2.
```
curl -i  --header "context-id: mycontextid" localhost:9001/api/v1/trace
```

<img width="300" src="https://user-images.githubusercontent.com/72261652/233176385-0f874b19-99e4-4232-a5c0-a21362c5df9f.png" />

---
### Example 2: OpenTelemetry agent

* app3 and app4 are Play Framework applications, while app5 is Zio HTTP, and app6 is Http4s
* All apps use OpenTelemetry for monitoring.
* OpenTelemetry agent sends tracing and metrics to OTEL.
* Prometheus:
  * http://localhost:9093
  * http://localhost:9094
  * http://localhost:9095
  * http://localhost:9096
* jaeger: http://localhost:16686

```
cd docker/opentelemetry && docker compose up
```
#### Getting trace_id: app3 -> (app4 || app5)
In the example, the user sends a request to app3 and the same trace id is sent and distributed to app4 and app5 in parallel.
```
curl -i  --header "context-id: mycontextid" localhost:9003/api/v1/trace
curl -i  --header "context-id: error" localhost:9003/api/v1/trace
```

<img width="300" src="https://user-images.githubusercontent.com/72261652/233178921-9a1bc156-f133-4702-9698-14b8453354cd.png" />

---
#### Gatling to build many transactions to display:
```
sbt -Dusers=1 -Dramp=1 -Dport=9000 Gatling/test [For kamon example]
sbt -Dusers=1 -Dramp=1 -Dport=9003 Gatling/test [For opentelemetry example]
```