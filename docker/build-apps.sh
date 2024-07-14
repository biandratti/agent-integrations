#!/bin/sh

echo "building app1 **************"
sbt app1/docker:publishLocal
echo "building app2 **************"
sbt app2/docker:publishLocal
echo "building app3 **************"
sbt app3/docker:publishLocal
echo "building app4 **************"
sbt app4/docker:publishLocal
echo "building app5 **************"
sbt app5/docker:publishLocal
echo "building app6 **************"
sbt app6/docker:publishLocal
