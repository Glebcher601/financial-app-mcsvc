#!/usr/bin/env bash
cd ./financial-domain-starter/ && gradle clean docker && cd .. &&
cd ./user-service/ && gradle clean docker && cd .. &&
cd ./financial-job/ && gradle clean docker && cd .. &&
cd ./authorization-server/ && gradle clean docker && cd .. &&
cd ./storage-service/ && gradle clean docker && cd .. &&
cd ./user-service/docker/db/ && docker build -t gcr.io/innate-life-217919/mysql:final .