# banking-app

This application is used for simulating contactless payments and enrolling new cards.

## Setup

This mobile project is really simple to use for development : clone the project and build it in Android Studio.

### WARNING

It might be possible that functions which use the Directions class could be not compiled the first time you will build the project.
Just remove one direction in one navigation graph and recreate it. Android Studio can then re-index directions properly.

If you want to test this app with successful payments, notice than the correct PIN is 753159.

Last thing: this app use free-plan heroku backend, so first time you run it the loading of cards could be longer.
