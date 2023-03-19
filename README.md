Monitoring:

app1:
```
sh runApp1.sh
sh runApp2.sh
sh runApp3.sh
```

### jaegertracing
```

docker run -d -p6831:6831/udp -p6832:6832/udp -p16686:16686 -p14268:14268 jaegertracing/all-in-one:latest
 or
docker-compose up

Opentelemetry
docker run --rm -it --name jaeger  -e COLLECTOR_OTLP_ENABLED=true   -p 4318:4317   -p 16686:16686   jaegertracing/all-in-one:1.39
```
http://localhost:16686

### test cross apps
```
curl -i  --header "context-id: maxi123" localhost:9000/api1/v1/trace
```