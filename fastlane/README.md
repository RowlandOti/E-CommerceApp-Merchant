fastlane documentation
----

# Installation

Make sure you have the latest version of the Xcode command line tools installed:

```sh
xcode-select --install
```

For _fastlane_ installation instructions, see [Installing _fastlane_](https://docs.fastlane.tools/#installing-fastlane)

# Available Actions

## Android

### android test

```sh
[bundle exec] fastlane android test
```

Runs all the tests

### android buildDebug

```sh
[bundle exec] fastlane android buildDebug
```

Builds the debug code

### android buildRelease

```sh
[bundle exec] fastlane android buildRelease
```

Builds the release code

### android beta

```sh
[bundle exec] fastlane android beta
```

Deploy a new Beta Build to Crashlytics Beta

### android deployInternal

```sh
[bundle exec] fastlane android deployInternal
```

Deploy a new Internal Build to Play Store

### android deployAlpha

```sh
[bundle exec] fastlane android deployAlpha
```

Deploy a new Alpha Build to Play Store

### android deployProduction

```sh
[bundle exec] fastlane android deployProduction
```

Deploy a new Production Build to the Play Store

### android deployDistribute

```sh
[bundle exec] fastlane android deployDistribute
```

Lane for distributing app using Firebase App Distributions

### android postDeployPublishRelease

```sh
[bundle exec] fastlane android postDeployPublishRelease
```

Generates and publishes release notes for release and creates the next tag

### android promoteInternalToAlpha

```sh
[bundle exec] fastlane android promoteInternalToAlpha
```

Promote Internal to Alpha

### android promoteAlphaToBeta

```sh
[bundle exec] fastlane android promoteAlphaToBeta
```

Promote Alpha to Beta

### android promoteBetaToProduction

```sh
[bundle exec] fastlane android promoteBetaToProduction
```

Promote Beta to Production

----

This README.md is auto-generated and will be re-generated every time [_fastlane_](https://fastlane.tools) is run.

More information about _fastlane_ can be found on [fastlane.tools](https://fastlane.tools).

The documentation of _fastlane_ can be found on [docs.fastlane.tools](https://docs.fastlane.tools).
