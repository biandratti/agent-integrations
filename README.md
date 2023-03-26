Monitoring using Kamon and OpenTelemetry agent:

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
Testing:
```
curl -i  --header "context-id: mycontextid" localhost:9001/api/v1/trace
```
Prometheus:

http://localhost:9091/metrics

http://localhost:9092/metrics

### OpenTelemetry (app3, app4) test cross apps
```
docker/opentelemetry/docker-compose up
curl -i  --header "context-id: mycontextid" localhost:9003/api/v1/trace
```

### jaegertracing
http://localhost:16686
