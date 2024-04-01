# chicken-farm

## Summary

This code will run on a Raspberry PI in a chicken barn. There are scales under the laying boxes that detect weight changes and computes the event (like chicken in, chicken-out-left-egg etc). These events will be sent by Messenger.

## TODO List Springboot

### Epic v1: Chicken scale recognizes weight change and sends message about state (which chicken, an egg) via Messenger

* (1) correct shutdown of mqtt user
* (1) understand mqtt, qos, retain and create tasks to adopt (https://mosquitto.org/man/mosquitto-conf-5.html)
* (1) endpoint to display messured weights
* (2) implement calibrate
* (2) remove setting of weight for box
* (2) write tests and mocks for mqtt client
* (3) BUG: wenn Ei gelegt und erkannt, tare-it oder wenn nicht, die letzten Hühner in Message packen


## TODO List Arduino

* (*) enable Master and Mock Arduino
* (2) send stati on dedicated topic with qos 1 (?)
* (2) bugfix stati not appear on topic
* (2) implement calibration
* (3) write header with message
* (3) mqtt server takes char array to print
* (3) set client-id, yes or no (?)
* (3) define message rentention and QOS
* (4) reconnect mqtt 


## TODO advanced features

* rollout of new sketch automatically over wifi or internet
* Integration test with whole process (testing)
* UI for all webservices
* persist chicken weight, box weight, uid-box-mapping to file
* integrate Prometheus
* event that second egg gets layed
