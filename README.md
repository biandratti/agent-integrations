Monitoring:

app1:
```
sh runApp1.sh
```

### jaegertracing
```
docker run -d -p6831:6831/udp -p6832:6832/udp -p16686:16686 -p14268:14268 jaegertracing/all-in-one:latest
```
http://localhost:16686