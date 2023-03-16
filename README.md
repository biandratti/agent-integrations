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
```
http://localhost:16686

### test cross apps
```
curl -i  --header "context-id: maxi123" localhost:9000/api1/v1/trace
```