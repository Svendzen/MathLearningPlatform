FROM hashicorp/consul:1.19.2
COPY ./consul-kv.json /usr/src/consul/
WORKDIR /usr/src/consul
ENV CONSUL_HTTP_ADDR=consul:8500
ENTRYPOINT until consul kv import @consul-kv.json; do echo "Waiting for Consul"; sleep 2; done