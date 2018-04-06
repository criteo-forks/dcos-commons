# Statsd config is required by DCOS, here is a dummy config
export STATSD_UDP_PORT=12
export STATSD_UDP_HOST=localhost
# This variable must be a valid integer
export PACKAGE_BUILD_TIME_EPOCH_MS=0

# Convenient defaults
export MESOS_ZK_URI=${MESOS_ZK_URI:-zk://mesos-master.service.consul:2181/mesos}
export SERVICE_TLD=${SERVICE_TLD:-service.consul}
export CRITEO_VIP_HOST_TLD=${CRITEO_VIP_HOST_TLD:-$CRITEO_DC.$CRITEO_ENV.crto.in}
if [ -z $CRITEO_ZK_HOST ]; then
  export CRITEO_ZK_HOST=$(dig +short SRV incubator-mbrugidou-zookeeper.service.par.consul | awk 'END {print $4":"$3}')
fi
# Define consul service name everywhere
export TASKCFG_ALL_CRITEO_CONSUL_SERVICE=$(tr ./ - <<< $SERVICE_NAME | sed 's/^-//')

# Fake DCOS version is required
export CRITEO_DCOS_VERSION=${CRITEO_DCOS_VERSION:-1.11.0}
