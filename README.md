# groheondus-to-mqtt-gw

Convert the grohe ondus data to mqtt messages

This application will post a MQTT message for each connected device.
The message aims to be compatible to the zigbee2mqtt messages

## Example message

The short message is already parsed/interpreted and contatins only the most relevant 
information.

```json
{
  "serialnumber": "123456789",
  "temperature": 22.7,
  "humidity": 40,
  "connection": "1",
  "battery": "100",
  "timestamp": "2019-05-17T01:55:21.000+02:00"
}
```

## Example configuration
see [config-example.json](config-example.json)

# build

build the docker container using `build.sh`

# run

copy the `config-example.json` to `/production/config/config.json`
and insert your grohe ondus username/password.

```
cd ./production
docker-compose up -d
```
