# chicken-farm

## Summary

This code will run on a Raspberry PI in a chicken barn. There are scales under the laying boxes that detect weight changes and computes the event (like chicken in, chicken-out-left-egg etc). These events will be sent by Whatsapp.

## TODO List

### Epic v1: Chicken scale recognizes weight change and sends message about state (which chicken, an egg) via Whatsapp

* Story 1: (done) create Webservice GET base
* Story 2: (done) using Github Actions to automatic build and create an image
* Story 3: install current Docker image on Raspberry PI and start it initially
* Story 4: read data from scale and show by webservice (GET)
* Story 5: permanently call scale to get current weight and calculate the state
* Story 6: integrate Whatsapp




