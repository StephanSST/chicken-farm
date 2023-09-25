# chicken-farm

## Summary

This code will run on a Raspberry PI in a chicken barn. There are scales under the laying boxes that detect weight changes and computes the event (like chicken in, chicken-out-left-egg etc). These events will be sent by Messenger.

## TODO List

### Epic v1: Chicken scale recognizes weight change and sends message about state (which chicken, an egg) via Messenger

* Story: Check if Slack is better than Threema
* Story: switch to feign client
* Story: fix disabled test
* Story: Integration test with whole process (testing)

* Story: (done) using Lombok to log
* Story: (done) respect both boxes in Schedule
* Story: (done) ENV VAR for "enabled" and Schedule "fixedRate"
* Story: (done) write tests and mock for scale service (testing)
* Story: (done) get current weight and calculate the "state" (chicken, egg)
* Story: (done) env values via application.yaml
* Story: (done) integrate Threema and send messages
* Story: (done) read data from scale and show by webservice (GET)
* Story: (done) change discovery webservice to use DO instead of strings
* Story: (done) create scale service that delivers current weight
* Story: (done) give calibrate webservice operation for load cell
* Story: (done) create structure with timer service pulling dummy scale service and show data via GET service
* Story: (done) install current Docker image on Raspberry PI and start it initially
* Story: (done) using Github Actions to automatic build and create an image
* Story: (done) create webservice GET base


### Epic v2: Recognize chicken
* Story: connect load cell UID with box number
* Story: persist chicken weight, box weight, uid-box-mapping to file
* Story: manage chickens and box
* Story: integrate Prometheus
