#!/bin/bash

GRADLE_OPTS="-Dconfig=local -Xmx512m -XX:+UseConcMarkSweepGC -Dapplication.name=badgerskt"

gradle run