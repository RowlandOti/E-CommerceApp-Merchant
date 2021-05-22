fastlane documentation
================
# Installation

Make sure you have the latest version of the Xcode command line tools installed:

```
xcode-select --install
```

Install _fastlane_ using
```
[sudo] gem install fastlane -NV
```
or alternatively using `brew install fastlane`

# Available Actions
## Android
### android test
```
fastlane android test
```
Runs all the tests
### android buildDebug
```
fastlane android buildDebug
```
Builds the debug code
### android buildRelease
```
fastlane android buildRelease
```
Builds the release code
### android beta
```
fastlane android beta
```
Deploy a new Beta Build to Crashlytics Beta
### android deployInternal
```
fastlane android deployInternal
```
Deploy a new Internal Build to Play Store
### android deployAlpha
```
fastlane android deployAlpha
```
Deploy a new Alpha Build to Play Store
### android deployProduction
```
fastlane android deployProduction
```
Deploy a new Production Build to the Play Store
### android deployDistribute
```
fastlane android deployDistribute
```
Lane for distributing app using Firebase App Distributions
### android promoteInternalToAlpha
```
fastlane android promoteInternalToAlpha
```
Promote Internal to Alpha
### android promoteAlphaToBeta
```
fastlane android promoteAlphaToBeta
```
Promote Alpha to Beta
### android promoteBetaToProduction
```
fastlane android promoteBetaToProduction
```
Promote Beta to Production

----

This README.md is auto-generated and will be re-generated every time [fastlane](https://fastlane.tools) is run.
More information about fastlane can be found on [fastlane.tools](https://fastlane.tools).
The documentation of fastlane can be found on [docs.fastlane.tools](https://docs.fastlane.tools).
