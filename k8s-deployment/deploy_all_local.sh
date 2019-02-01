#!/usr/bin/env bash

cd ./mongo/local/ && kubectl apply -f . && cd ../../ &&
cd ./mysql/local/ && kubectl apply -f . && cd ../../ &&
cd ./prometheus/ && kubectl apply -f . && cd ../ &&
kubectl apply -f .