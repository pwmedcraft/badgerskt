#!/bin/bash
dir="$( cd "$( dirname "$0" )" && pwd )"
cd ${dir}/..

echo "Testing back end"
gradle clean test:compile test

#echo "Testing front end"
#cd ui
#npm test etc