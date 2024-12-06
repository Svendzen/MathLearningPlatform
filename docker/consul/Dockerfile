FROM hashicorp/consul:1.19.2

# Copy KV configuration file
COPY ./consul-kv.json /consul/config/

# Copy the Consul configuration directory
COPY ./config /consul/config

# Set working directory for clarity
WORKDIR /consul/config

# Environment variable for Consul address
ENV CONSUL_HTTP_ADDR=consul:8500

ENTRYPOINT until consul kv import @consul-kv.json; do echo "Waiting for Consul"; sleep 2; done