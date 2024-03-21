# chicken-farm

## Summary

This code will run on a Raspberry PI in a chicken barn. There are scales under the laying boxes that detect weight changes and computes the event (like chicken in, chicken-out-left-egg etc). These events will be sent by Messenger.

## TODO List Springboot

### Epic v1: Chicken scale recognizes weight change and sends message about state (which chicken, an egg) via Messenger

* understand mqtt, qos, retain and create tasks to adopt
* implement calibrate
* write tests and mocks for mqtt client
* correct shutdown of mqtt user
* map log path to file system in Docker
* ping message to check scale
* endpoint to display state and messages


## TODO List Arduino

* send stati on dedicated topic with qos 1 (?)
* bugfix stati not appear on topic
* implement calibration
* pong message to confirm availability




## TODO advanced features

* Integration test with whole process (testing)
* UI for all webservices
* persist chicken weight, box weight, uid-box-mapping to file
* integrate Prometheus
* event that second egg gets layed
