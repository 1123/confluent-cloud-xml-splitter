#!/bin/bash

set -u -e

while true; do
  cat sample.xml
  sleep 1
done | kafka-console-producer \
  --broker-list $BOOTSTRAP_SERVERS \
  --producer-property bootstrap.servers=$BOOTSTRAP_SERVERS \
  --producer-property ssl.endpoint.identification.algorithm=https \
  --producer-property security.protocol=SASL_SSL \
  --producer-property sasl.mechanism=PLAIN \
  --producer-property sasl.jaas.config="${SASL_JAAS_CONFIG}" \
  --topic kafka-xml-splitter-input


