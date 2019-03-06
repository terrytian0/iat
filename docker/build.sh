#!/bin/bash
unzip ROOT.war -d ./ROOT
docker build --no-cache=true -t "terrytian/iat:v0.2" .
rm -r ./ROOT
