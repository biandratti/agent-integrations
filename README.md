Monitoring:

Run apps without agent:
```
sh runApp1.sh
sh runApp2.sh
sh runApp3.sh
```

Run apps without agent:
```
sbt app1/docker:publishLocal
sbt app2/docker:publishLocal
sbt app3/docker:publishLocal
```

### jaegertracing
```
docker-compose up
```

http://localhost:16686

### test cross apps
```
curl -i  --header "context-id: mycontextid" localhost:9000/api1/v1/trace
```