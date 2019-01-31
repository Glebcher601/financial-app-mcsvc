#!/usr/bin/env bash

kubectl delete -f . &&
cd ./prometheus/ && kubectl delete -f . && cd ../ &&
cd ./mongo/local/ && kubectl delete -f . && cd ../../ &&
cd ./mysql/local/ && kubectl delete -f . && cd ../../