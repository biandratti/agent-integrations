# monitoring [![Build Status](https://github.com/biandratti/play-monitoring/actions/workflows/ci.yml/badge.svg?branch=master)](https://github.com/biandratti/play-monitoring/actions/workflows/ci.yml) [![codecov](https://codecov.io/gh/biandratti/play-monitoring/branch/master/graph/badge.svg?token=MMS4N0N8KQ)](https://codecov.io/gh/biandratti/play-monitoring)

Monitoring as an example using Kamon and OpenTelemetry agents:

Run apps with agent:
```
sbt app1/docker:publishLocal
sbt app2/docker:publishLocal
sbt app3/docker:publishLocal
sbt app4/docker:publishLocal
```

### Kamon (app1, app2) test cross apps
```
docker/kammon/docker-compose up
```
Getting trace_id: app1 -> app2
```
curl -i  --header "context-id: mycontextid" localhost:9001/api/v1/trace
```
Prometheus:

http://localhost:9091/metrics

http://localhost:9092/metrics

### OpenTelemetry (app3, app4) test cross apps
```
docker/opentelemetry/docker-compose up
```
Getting trace_id: app3 -> app4
```
curl -i  --header "context-id: mycontextid" localhost:9003/api/v1/trace
```

Prometheus:

http://localhost:9093

http://localhost:9094

### jaegertracing
http://localhost:16686
