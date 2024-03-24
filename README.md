# chicken-farm

## Summary

This code will run on a Raspberry PI in a chicken barn. There are scales under the laying boxes that detect weight changes and computes the event (like chicken in, chicken-out-left-egg etc). These events will be sent by Messenger.

## TODO List Springboot

### Epic v1: Chicken scale recognizes weight change and sends message about state (which chicken, an egg) via Messenger

* BUG: Observer muss regelmässiger laufen, abstimmen mit Mqtt Message
* BUG: wenn Ei gelegt und erkannt, tare-it oder wenn nicht, die letzten Hühner in Message packen
* correct shutdown of mqtt user
* implement calibrate
* write tests and mocks for mqtt client
* ping message to check scale
* endpoint to display state and messages
* endpoint to set current weight, box-state and chicken
* understand mqtt, qos, retain and create tasks to adopt


## TODO List Arduino

* send stati on dedicated topic with qos 1 (?)
* bugfix stati not appear on topic
* implement calibration
* pong message to confirm availability
* write header with message




## TODO advanced features

* Integration test with whole process (testing)
* UI for all webservices
* persist chicken weight, box weight, uid-box-mapping to file
* integrate Prometheus
* event that second egg gets layed
