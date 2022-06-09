# CHANGELOG

## Unreleased

Changes unreleased.

### Bug Fixes

- general:
  - resolve crashes and bug ([1c8f366](https://github.com/RowlandOti/E-CommerceApp-Merchant/commit/1c8f3661cbb8e30fb1d5cf05d939d08a35012f49))

### Refactors

- general:
  - remove try-catch out ([629c862](https://github.com/RowlandOti/E-CommerceApp-Merchant/commit/629c86291f8c716fa324ab38f553a2fa333dc98b))

## [refs/heads/release](https://github.com/RowlandOti/E-CommerceApp-Merchant/releases/tag/refs/heads/release) - 2021-05-31 21:12:54

*No description*

### Continuous Integration

- general:
  - specify, which branch release filkes get uploaded ([666188f](https://github.com/RowlandOti/E-CommerceApp-Merchant/commit/666188fc142b9bd543cb3000fdd8d5515c7c7cef))
  - fetch full depth of git history and tags ([9cf663f](https://github.com/RowlandOti/E-CommerceApp-Merchant/commit/9cf663ffb1d660260b67a224efb3335bc522d1f0))
  - update fastlane ([38a558d](https://github.com/RowlandOti/E-CommerceApp-Merchant/commit/38a558d2e3ca62a434344e693ba5e23f1eb3305c))
  - add step for building apk/bundle to workflow ([58a5473](https://github.com/RowlandOti/E-CommerceApp-Merchant/commit/58a5473a3f1a4f1973153cbba65549974be8b5e4))
  - remove changelog category limits - log all ([fc9d8d2](https://github.com/RowlandOti/E-CommerceApp-Merchant/commit/fc9d8d2fb5ec74793a07e88460feeb362e5893b8))
  - generate change_log from commits ([09c8064](https://github.com/RowlandOti/E-CommerceApp-Merchant/commit/09c8064a9159803a96456b2a19c415d3d1afe9ac))
  - remove storing to ENV ([e6079f1](https://github.com/RowlandOti/E-CommerceApp-Merchant/commit/e6079f148fcf72200a188a3934b0ba01c528fbac))
  - remove storing to ENV ([b2191dc](https://github.com/RowlandOti/E-CommerceApp-Merchant/commit/b2191dc72e32066a3368a18a67ae405be61170fc))
  - trigger release on release branch ([30b673f](https://github.com/RowlandOti/E-CommerceApp-Merchant/commit/30b673f921fbbf04510e6c7ff8d627b56a657f21))
  - use analyse commits to get next release ([a17f38b](https://github.com/RowlandOti/E-CommerceApp-Merchant/commit/a17f38bf48afaea9bf3480ca58e03af78a15aa16))
  - fix gradle file path typo ([2071126](https://github.com/RowlandOti/E-CommerceApp-Merchant/commit/2071126d6a31859e80fb469ab1732dddc511b80c))
  - fix gradle file path typo ([eb4e267](https://github.com/RowlandOti/E-CommerceApp-Merchant/commit/eb4e26737cebcdc28c8138ea001fda64f88e6ae3))
  - read and publish version info via fastlane ([85aa94a](https://github.com/RowlandOti/E-CommerceApp-Merchant/commit/85aa94a27e91f5c41d3de484d7ca25c6e51a1710))
  - log version code and name on terminal ([4ce2d8f](https://github.com/RowlandOti/E-CommerceApp-Merchant/commit/4ce2d8f9492e04b318e5acb6e59b370cb372d4df))
  - skip uploading apk ([a66238f](https://github.com/RowlandOti/E-CommerceApp-Merchant/commit/a66238feac0c022df0803e45d943bdba9603b92e))
  - ci-fix path to keystore b64 ([9367d3b](https://github.com/RowlandOti/E-CommerceApp-Merchant/commit/9367d3b43616b8dbec4207c019298b266ef55954))
  - fix versionCode missing in manifest bug ([131693e](https://github.com/RowlandOti/E-CommerceApp-Merchant/commit/131693ea9324a26b33bd9099d36d0a01fdb357da))
  - only generate apk for release and not distribution ([f5f3b9c](https://github.com/RowlandOti/E-CommerceApp-Merchant/commit/f5f3b9cd5887666eef33c75e3a207f1b6e9d51c7))
  - fix bug where version code wasn't being generated ([d5bb70a](https://github.com/RowlandOti/E-CommerceApp-Merchant/commit/d5bb70a27fa35334c1975b75861c20b428d3a579))
  - refer to keystore file stored in root ([303db68](https://github.com/RowlandOti/E-CommerceApp-Merchant/commit/303db68cf40c3bf79f5e9ae7d1e4aa1d5e47dcde))
  - refer to keystore file stored in root ([d89bf7a](https://github.com/RowlandOti/E-CommerceApp-Merchant/commit/d89bf7a8a6e99ae04a64017065618350504c6963)) ([#20](https://github.com/RowlandOti/E-CommerceApp-Merchant/pull/20))
  - remove second - in step , seems to fail with it ([55d2ac0](https://github.com/RowlandOti/E-CommerceApp-Merchant/commit/55d2ac04984b32187dffb8f25dcd32ecdbaf9fb4)) ([#19](https://github.com/RowlandOti/E-CommerceApp-Merchant/pull/19))
  - fix typo in referencing firebase config file ([fecece4](https://github.com/RowlandOti/E-CommerceApp-Merchant/commit/fecece49fa6cff0d5811a07714177b971852613d)) ([#19](https://github.com/RowlandOti/E-CommerceApp-Merchant/pull/19))
  - remove redundnat needs statement ([c96b293](https://github.com/RowlandOti/E-CommerceApp-Merchant/commit/c96b293e1c18b7610dcda3851b4be78b1c09d098)) ([#19](https://github.com/RowlandOti/E-CommerceApp-Merchant/pull/19))
  - format yaml file ([ae47021](https://github.com/RowlandOti/E-CommerceApp-Merchant/commit/ae470218810bacca368b558f84774e0612bece0c)) ([#19](https://github.com/RowlandOti/E-CommerceApp-Merchant/pull/19))
  - modify release workflow to generate build apk/aaab to github release ([3fca91c](https://github.com/RowlandOti/E-CommerceApp-Merchant/commit/3fca91c959593a32865c3a78010887b9dee13df3)) ([#18](https://github.com/RowlandOti/E-CommerceApp-Merchant/pull/18))
  - ensure to run bundle update first ([bf178e0](https://github.com/RowlandOti/E-CommerceApp-Merchant/commit/bf178e062c15923c0ba046dc2e6968d12c9739d6)) ([#17](https://github.com/RowlandOti/E-CommerceApp-Merchant/pull/17))
  - produce both apk and aab ([c9e0b94](https://github.com/RowlandOti/E-CommerceApp-Merchant/commit/c9e0b94408d73c2f33932a2362914cfdbb3041b9)) ([#17](https://github.com/RowlandOti/E-CommerceApp-Merchant/pull/17))
  - specify mac-os version ([8b20b7e](https://github.com/RowlandOti/E-CommerceApp-Merchant/commit/8b20b7e5144a0efa1e9b4e8ec353bc798d40afee)) ([#16](https://github.com/RowlandOti/E-CommerceApp-Merchant/pull/16))
  - change target version ([f4ddb8f](https://github.com/RowlandOti/E-CommerceApp-Merchant/commit/f4ddb8f659c2b436965179eb22ea7ff005149b15)) ([#16](https://github.com/RowlandOti/E-CommerceApp-Merchant/pull/16))
  - collect test reports ([1d5ab04](https://github.com/RowlandOti/E-CommerceApp-Merchant/commit/1d5ab0462d802950e768cdc1a513e7a2d79d3c7d)) ([#16](https://github.com/RowlandOti/E-CommerceApp-Merchant/pull/16))
  - collect test reports ([a545ae3](https://github.com/RowlandOti/E-CommerceApp-Merchant/commit/a545ae3584e0d31d260369fdf3d11ca260a1e30d)) ([#16](https://github.com/RowlandOti/E-CommerceApp-Merchant/pull/16))
  - generate keys for all jobs ([076eab1](https://github.com/RowlandOti/E-CommerceApp-Merchant/commit/076eab1ada4cedc19a390396f58d95c715a9f4a3)) ([#16](https://github.com/RowlandOti/E-CommerceApp-Merchant/pull/16))
  - use relative paths for keystore, declare keystore in build.gradle ([5d2e842](https://github.com/RowlandOti/E-CommerceApp-Merchant/commit/5d2e84287f32dc3840f6297bb8d90399dfcbbdf0)) ([#15](https://github.com/RowlandOti/E-CommerceApp-Merchant/pull/15))
  - add change log file generation work flow ([1a0dce8](https://github.com/RowlandOti/E-CommerceApp-Merchant/commit/1a0dce817ca0a45d4072c4012b19da78a326ec4d)) ([#15](https://github.com/RowlandOti/E-CommerceApp-Merchant/pull/15))
  - typo in yaml file fix,  remove extra spacing ([3869023](https://github.com/RowlandOti/E-CommerceApp-Merchant/commit/3869023bf0697723ea7f7e5d6d1e3fd2cacffeff)) ([#14](https://github.com/RowlandOti/E-CommerceApp-Merchant/pull/14))
  - ci configure aab upload for firebase distribution ([0fc79df](https://github.com/RowlandOti/E-CommerceApp-Merchant/commit/0fc79df3df951656d54371e79e9345a37a381bca)) ([#14](https://github.com/RowlandOti/E-CommerceApp-Merchant/pull/14))
  - rename job to include name of destination of deploy ([b752f31](https://github.com/RowlandOti/E-CommerceApp-Merchant/commit/b752f314770f272888b740d59b320db54496c6a4)) ([#14](https://github.com/RowlandOti/E-CommerceApp-Merchant/pull/14))
  - add distribution lists and firebase services account to ci ([92d9d67](https://github.com/RowlandOti/E-CommerceApp-Merchant/commit/92d9d67173c6cc741a64e0797b3d47e79453fa23)) ([#13](https://github.com/RowlandOti/E-CommerceApp-Merchant/pull/13))
  - fix typo in yaml file ([3c5c66b](https://github.com/RowlandOti/E-CommerceApp-Merchant/commit/3c5c66bc7508457d24208a408e2532e4d735ee1d)) ([#12](https://github.com/RowlandOti/E-CommerceApp-Merchant/pull/12))
  - uses correct gcp services key path ([5b4a2cc](https://github.com/RowlandOti/E-CommerceApp-Merchant/commit/5b4a2cce9dfcad8c14e57807a272b72c36a5c987)) ([#10](https://github.com/RowlandOti/E-CommerceApp-Merchant/pull/10))
  - only run ui test for ui module ([e407680](https://github.com/RowlandOti/E-CommerceApp-Merchant/commit/e4076804c7a57af54ff134ae97a3d79348cb2193)) ([#10](https://github.com/RowlandOti/E-CommerceApp-Merchant/pull/10))
  - fix invlaid branch arg declaration ([1aa86ea](https://github.com/RowlandOti/E-CommerceApp-Merchant/commit/1aa86ea8c5c20c1e78633297ce2d6630b8ad4c5d)) ([#10](https://github.com/RowlandOti/E-CommerceApp-Merchant/pull/10))
  - target main branch ([03a0605](https://github.com/RowlandOti/E-CommerceApp-Merchant/commit/03a0605979b327c2c0782266220efaea761fd380)) ([#10](https://github.com/RowlandOti/E-CommerceApp-Merchant/pull/10))
  - add fastlane to ci for deployments to playstore and distribution lists ([bb0c8ae](https://github.com/RowlandOti/E-CommerceApp-Merchant/commit/bb0c8ae13af6c9cb399f1d0a1f7a64079e708a06)) ([#10](https://github.com/RowlandOti/E-CommerceApp-Merchant/pull/10))
  - only run ui test for android modules ([97d0e6d](https://github.com/RowlandOti/E-CommerceApp-Merchant/commit/97d0e6d3320adee003dd07b41089f71486c5f6fd)) ([#10](https://github.com/RowlandOti/E-CommerceApp-Merchant/pull/10))

### Tests

- general:
  - ignore flaky test ([71db6b9](https://github.com/RowlandOti/E-CommerceApp-Merchant/commit/71db6b9731b80d52e2f61eed75003fea832ce44d)) ([#16](https://github.com/RowlandOti/E-CommerceApp-Merchant/pull/16))
  - resolve flaky test that dependend on Firebase Auth ([0db7fcd](https://github.com/RowlandOti/E-CommerceApp-Merchant/commit/0db7fcdaa7c29ba59da9906ea16b6d3e09f6ad7f)) ([#15](https://github.com/RowlandOti/E-CommerceApp-Merchant/pull/15))
  - fix failing test cases ([cd872d9](https://github.com/RowlandOti/E-CommerceApp-Merchant/commit/cd872d9742699f565f1ebed807ef66493b60f7a5)) ([#10](https://github.com/RowlandOti/E-CommerceApp-Merchant/pull/10))
  - update tests dependencies ([fec8657](https://github.com/RowlandOti/E-CommerceApp-Merchant/commit/fec865774c697b10298ff8b37d563a9883fe04a3)) ([#10](https://github.com/RowlandOti/E-CommerceApp-Merchant/pull/10))
  - fix missing ui test dependencies ([bb046a9](https://github.com/RowlandOti/E-CommerceApp-Merchant/commit/bb046a92b5cd060cccfc03e61caf274ac03ebded)) ([#10](https://github.com/RowlandOti/E-CommerceApp-Merchant/pull/10))
  - correctly qualify test, make getActivity method an extension ([9fbe5d1](https://github.com/RowlandOti/E-CommerceApp-Merchant/commit/9fbe5d11c95307660c15932101ce7b2b548401a1)) ([#10](https://github.com/RowlandOti/E-CommerceApp-Merchant/pull/10))
  - tests: fixed login/register flow tests ([b25cef8](https://github.com/RowlandOti/E-CommerceApp-Merchant/commit/b25cef8f493d8ce99d0adfdb30aeda238d9ccbae)) ([#10](https://github.com/RowlandOti/E-CommerceApp-Merchant/pull/10))
  - write Expresso tests for auth screen ([80c2ce6](https://github.com/RowlandOti/E-CommerceApp-Merchant/commit/80c2ce641a4df75a0708227ffb4394c05335d403)) ([#8](https://github.com/RowlandOti/E-CommerceApp-Merchant/pull/8))
  - tests: use spoon for running some instrumented tests ([ed290b1](https://github.com/RowlandOti/E-CommerceApp-Merchant/commit/ed290b1a752f8ea92d374f2dbe42f13cac5063f8))
  - fix failing test ([554b37b](https://github.com/RowlandOti/E-CommerceApp-Merchant/commit/554b37bc8f68ea371f8daf61c13fe2b0e2430888))
  - tests: write some unit tests for the domain and data layers ([5ed2aea](https://github.com/RowlandOti/E-CommerceApp-Merchant/commit/5ed2aea7679d3a76b052dd119f56b7456c786d51))
  - tests: write some unit tests for the domain and data layers ([a894169](https://github.com/RowlandOti/E-CommerceApp-Merchant/commit/a894169ef0b4c9eed9fbab72412e21d0ce76d6ba))
  - tests: write some unit tests for the domain and data layers ([a2fa1d6](https://github.com/RowlandOti/E-CommerceApp-Merchant/commit/a2fa1d6e0596d546b0b94c4a65f75efcf768c50e))
  - tests: write some unit tests for the domain and data layers ([bf4beac](https://github.com/RowlandOti/E-CommerceApp-Merchant/commit/bf4beac5a4ba1b228b59dedda3b573be59065a3f))
  - remove redundant mocks ([efc6505](https://github.com/RowlandOti/E-CommerceApp-Merchant/commit/efc650566268f4a16f606dacf47dffc3c2c92b49))
  - retire test for fixing later ([4454668](https://github.com/RowlandOti/E-CommerceApp-Merchant/commit/4454668dd611ae38b0b1c1f7d33764781f9e1610))
  - use mockito dependency ([33a716e](https://github.com/RowlandOti/E-CommerceApp-Merchant/commit/33a716e5b3696604663a96043ec039f4632b05f7))

### New Features

- general:
  - added encrypted shared preferences package ([0b84c3d](https://github.com/RowlandOti/E-CommerceApp-Merchant/commit/0b84c3d96ec7f8d43093e8dc89553ed4032e2950)) ([#10](https://github.com/RowlandOti/E-CommerceApp-Merchant/pull/10))
  - added encrypted shared preferences package ([c41799a](https://github.com/RowlandOti/E-CommerceApp-Merchant/commit/c41799aace37898891b3e9205b29969d701ccb06)) ([#10](https://github.com/RowlandOti/E-CommerceApp-Merchant/pull/10))
  - use last updated image ([b15bb09](https://github.com/RowlandOti/E-CommerceApp-Merchant/commit/b15bb0961daf3d59710da93af6550fdf785b4635))
  - allow update of multiple images ([ae8ea27](https://github.com/RowlandOti/E-CommerceApp-Merchant/commit/ae8ea27f323041aa0d3eb918f32f33cab74905ff))
  - edit product and update feature now supported ([577da90](https://github.com/RowlandOti/E-CommerceApp-Merchant/commit/577da901bf82d5e9c7b19514759e08410e83f127))
  - add shuflle module ([092695a](https://github.com/RowlandOti/E-CommerceApp-Merchant/commit/092695a9b585562b2af411e1ecb46a976b0b8ba6))
  - re-itration on shuffle problem. Attempted fix ([ed87f0c](https://github.com/RowlandOti/E-CommerceApp-Merchant/commit/ed87f0c0c9e23fac3be9c092dee0312ff52e807a))
  - associate more than one image per product ([e95fe67](https://github.com/RowlandOti/E-CommerceApp-Merchant/commit/e95fe67e7c289c0d7a48a6eb2b2eb207759b57c2))
  - add downloadble fonts ([68f8727](https://github.com/RowlandOti/E-CommerceApp-Merchant/commit/68f8727a43624dd1789929cd65de065ac72bd833))
  - use downloadble fonts ([1d1cb2a](https://github.com/RowlandOti/E-CommerceApp-Merchant/commit/1d1cb2a80a07cdab77085c3210f9a9342b51c6c6))
  - select more than one image ([e5a1295](https://github.com/RowlandOti/E-CommerceApp-Merchant/commit/e5a1295dc82e15a3a9547090920a5fd91fb82b64))
  - adding delete feat ([4a53a49](https://github.com/RowlandOti/E-CommerceApp-Merchant/commit/4a53a49d9a33bb38134e3942cca307cb290ecab6))
  - manage state views ([21b4d41](https://github.com/RowlandOti/E-CommerceApp-Merchant/commit/21b4d41d7461603169c99b6345513243b9100b98))
  - add product featured ([3b2cb7c](https://github.com/RowlandOti/E-CommerceApp-Merchant/commit/3b2cb7c32bf39243228bba99f8e7fc4ea926d034))
  - allowstatus to be modified ([d0c8a5e](https://github.com/RowlandOti/E-CommerceApp-Merchant/commit/d0c8a5ece478c71e6381f8993783aa59b966dff4))
  - feat-add orders ([81ec957](https://github.com/RowlandOti/E-CommerceApp-Merchant/commit/81ec957559b336f42fb86960a458bbc010305d3e))
  - add category feat ([016e05c](https://github.com/RowlandOti/E-CommerceApp-Merchant/commit/016e05c58d28e8e325601d31004c529e9fb30f28))

### Bug Fixes

- general:
  - version code nees to be a name ([7c4cd2a](https://github.com/RowlandOti/E-CommerceApp-Merchant/commit/7c4cd2aeaab328828b59941286ccce9569257d5e))
  - resolve publishing to playstire issue ([513c5c1](https://github.com/RowlandOti/E-CommerceApp-Merchant/commit/513c5c165131c029f24b77bfb205b7aa1f108ac4))
  - make toolbar support back navigation ([eb9becd](https://github.com/RowlandOti/E-CommerceApp-Merchant/commit/eb9becd2021f5821d537f23b1405576b6028e605)) ([#8](https://github.com/RowlandOti/E-CommerceApp-Merchant/pull/8))
  - use Kotlin round func ([6b8988e](https://github.com/RowlandOti/E-CommerceApp-Merchant/commit/6b8988e7a6e85d381c54087bd0fe628c8b2e1e7d)) ([#3](https://github.com/RowlandOti/E-CommerceApp-Merchant/pull/3))
  - use Kotlin round func ([df92621](https://github.com/RowlandOti/E-CommerceApp-Merchant/commit/df92621c5f869d0d2a87dabfac904161b2fdbe70)) ([#3](https://github.com/RowlandOti/E-CommerceApp-Merchant/pull/3))
  - rectified problem of auth textviews being cut-off to the right ([3a81e64](https://github.com/RowlandOti/E-CommerceApp-Merchant/commit/3a81e640af9bf37c8d269005c2818faf5e23a661)) ([#3](https://github.com/RowlandOti/E-CommerceApp-Merchant/pull/3))
  - use a more determinate Toolbar color as default. ([f9b83ce](https://github.com/RowlandOti/E-CommerceApp-Merchant/commit/f9b83ce71791feda67489794e2f45cff11802e54)) ([#3](https://github.com/RowlandOti/E-CommerceApp-Merchant/pull/3))
  - clear old list in case category changes ([f19cebf](https://github.com/RowlandOti/E-CommerceApp-Merchant/commit/f19cebfe3ccfd194d97370aca33f6531ffc47ab2)) ([#3](https://github.com/RowlandOti/E-CommerceApp-Merchant/pull/3))
  - One more time fixed bug causing duplicate fragments on rotation. Please check if it exists first by tag used to add it. ([5916d6e](https://github.com/RowlandOti/E-CommerceApp-Merchant/commit/5916d6ef9aa612a76318bb33583646f5c8d68308))
  - fixed bug cuaing duplicate frgaments on rotation. Please check if it exists first by tag used to add it. ([427b661](https://github.com/RowlandOti/E-CommerceApp-Merchant/commit/427b66101ae35128855ce7c2f204c9933cb84fc4))
  - ignore some IDE files. Not important ([9a47c1e](https://github.com/RowlandOti/E-CommerceApp-Merchant/commit/9a47c1e86b23e29623adb3bc7931e5649a996c83))
  - fix the crashes due to conflicting conflict names in AndroidX namespace and other libraries ([ba8ec4c](https://github.com/RowlandOti/E-CommerceApp-Merchant/commit/ba8ec4c4a233625b8b3d2257036170639fd5cf18))
  - ignore some IDE files. Not important ([3029c86](https://github.com/RowlandOti/E-CommerceApp-Merchant/commit/3029c86f5d306368b2b8066bebc031a71412e129))
  - fix the crashes due to conflicting conflict names in AndroidX namespace and other libraries ([358e03b](https://github.com/RowlandOti/E-CommerceApp-Merchant/commit/358e03b01d3d0247f36dd61ce9706942c3d741f5))
  - remove indexing warning ([f91d0f4](https://github.com/RowlandOti/E-CommerceApp-Merchant/commit/f91d0f43849ab29226b99a233a3777bc406bedc7))
  - make scope of ViewModel sharable across two Fragments with activity scope ([2815cd0](https://github.com/RowlandOti/E-CommerceApp-Merchant/commit/2815cd0ca0f5d136c760cb6ef32d4df892909be4))
  - temprorary fix for ugrading to gradle plugin 3.2.0 for ids without content ([cd9d98a](https://github.com/RowlandOti/E-CommerceApp-Merchant/commit/cd9d98aaa463c87db7f08f05228e4a2d33d4dc88))
  - fix bugs ([8913524](https://github.com/RowlandOti/E-CommerceApp-Merchant/commit/89135242bad95732a5408d92c54b9cfa641a2dae))
  - fix kotlin error ([28f5d6d](https://github.com/RowlandOti/E-CommerceApp-Merchant/commit/28f5d6d81f06dd7525556420075ae6e067b774ac))
  - fix bug introduced by upgrade to latest firebase-storage version ([67632f3](https://github.com/RowlandOti/E-CommerceApp-Merchant/commit/67632f3bd0173a9808eb1835a77eed92a0df675a))
  - only get correct category items ([6595954](https://github.com/RowlandOti/E-CommerceApp-Merchant/commit/6595954255b63bddb3ed8c3fd44b1bc409436884))
  - move toolbar to fragments. This will acoomadate a One Activity-mant  Fragment app ([7f47713](https://github.com/RowlandOti/E-CommerceApp-Merchant/commit/7f47713d6866294a4a2bbb38c9f942119dba1553))
  - rectify rotation crash bug. For injection before calling Activity#super#onCreate() ([3808a57](https://github.com/RowlandOti/E-CommerceApp-Merchant/commit/3808a57277e4f424bf868a509a3f93165075cbc5))

### Code Style Fixes

- general:
  - fix missing test dependency version ([6cd557d](https://github.com/RowlandOti/E-CommerceApp-Merchant/commit/6cd557dedea09cfd133baa8b5b89ddc945dbe301)) ([#5](https://github.com/RowlandOti/E-CommerceApp-Merchant/pull/5))

### Documentation

- general:
  - add ci status badge to README.md ([0b61130](https://github.com/RowlandOti/E-CommerceApp-Merchant/commit/0b611301b68dfb9373f43b0e8a9ba5776139eb4e))
  - add CHANGELOG.md ([33cc717](https://github.com/RowlandOti/E-CommerceApp-Merchant/commit/33cc7177fd2bbb359ce043ff0b09aa7c146ab7ef)) ([#14](https://github.com/RowlandOti/E-CommerceApp-Merchant/pull/14))
  - add Bitrise badge ([0ad49bd](https://github.com/RowlandOti/E-CommerceApp-Merchant/commit/0ad49bdfbf9b52f7d3644efdc141d9f2a19d4704))

### Refactors

- general:
  -  use better method for logout ([3c66b4d](https://github.com/RowlandOti/E-CommerceApp-Merchant/commit/3c66b4d1c1fd0ce2d029f0b0020b2c560fb12f78)) ([#10](https://github.com/RowlandOti/E-CommerceApp-Merchant/pull/10))
  - refactor to use encrypted shared preferences ([1fdd3c4](https://github.com/RowlandOti/E-CommerceApp-Merchant/commit/1fdd3c46c2c831f57815a2cc6dcbdecb1b00470c)) ([#10](https://github.com/RowlandOti/E-CommerceApp-Merchant/pull/10))
  - change session module di path ([1ccffbf](https://github.com/RowlandOti/E-CommerceApp-Merchant/commit/1ccffbf9d9b5cf93773e59fa631288527623b555)) ([#10](https://github.com/RowlandOti/E-CommerceApp-Merchant/pull/10))
  - refactor to use encrypted shared preferences ([89ec6be](https://github.com/RowlandOti/E-CommerceApp-Merchant/commit/89ec6be59f800b46f048762c5d4becd5dca6bbf8)) ([#10](https://github.com/RowlandOti/E-CommerceApp-Merchant/pull/10))
  - used inline to make func with func params more efficient. ([2b8a5f5](https://github.com/RowlandOti/E-CommerceApp-Merchant/commit/2b8a5f5e6d698ab168603527129c823a5b04ce66)) ([#10](https://github.com/RowlandOti/E-CommerceApp-Merchant/pull/10))
  - nullify content description accesibility requirement for silent image view ([78d96c9](https://github.com/RowlandOti/E-CommerceApp-Merchant/commit/78d96c9c29d37978e5b96f8a2b80686931014180)) ([#10](https://github.com/RowlandOti/E-CommerceApp-Merchant/pull/10))
  - extract strings into strings.xml ([0a0e439](https://github.com/RowlandOti/E-CommerceApp-Merchant/commit/0a0e439624bc7e7aa1f7eafb2915deec113fafcd)) ([#10](https://github.com/RowlandOti/E-CommerceApp-Merchant/pull/10))
  - everthing now uses one common toolbar from host activity ([dda7a12](https://github.com/RowlandOti/E-CommerceApp-Merchant/commit/dda7a12d13c26638cf18b3d4a0891539ebade2f9)) ([#8](https://github.com/RowlandOti/E-CommerceApp-Merchant/pull/8))
  - migrate to bottom navigator, sync actions with viewpager2 ([e38df49](https://github.com/RowlandOti/E-CommerceApp-Merchant/commit/e38df494c3300f8a962c5c66418cccd50833ff96)) ([#8](https://github.com/RowlandOti/E-CommerceApp-Merchant/pull/8))
  - move fake di package ([6f70e0b](https://github.com/RowlandOti/E-CommerceApp-Merchant/commit/6f70e0b4ba355c3761a6089d634667aeb3ae259a)) ([#8](https://github.com/RowlandOti/E-CommerceApp-Merchant/pull/8))
  - umove call to finish() in outer block ([c2cd4b6](https://github.com/RowlandOti/E-CommerceApp-Merchant/commit/c2cd4b63a9c5976e1abe99fcdf4a99c5459c7c79)) ([#8](https://github.com/RowlandOti/E-CommerceApp-Merchant/pull/8))
  - use completable to time ([48783d8](https://github.com/RowlandOti/E-CommerceApp-Merchant/commit/48783d80b5d779fb7617aea8a75d54b7e0dce67a)) ([#8](https://github.com/RowlandOti/E-CommerceApp-Merchant/pull/8))
  - upgraded support library ([f6985bf](https://github.com/RowlandOti/E-CommerceApp-Merchant/commit/f6985bf0f73e84d42bb6c94020f3f3b4c9820fe9)) ([#5](https://github.com/RowlandOti/E-CommerceApp-Merchant/pull/5))
  - shift from kotlin syntaxic laout to view binder ([e37c4ed](https://github.com/RowlandOti/E-CommerceApp-Merchant/commit/e37c4edfde3706410f5226818f77879ca269197f)) ([#5](https://github.com/RowlandOti/E-CommerceApp-Merchant/pull/5))
  - remove unneeded impl for Completeable ([00d65e1](https://github.com/RowlandOti/E-CommerceApp-Merchant/commit/00d65e1c904eb861e180735dc481bacfdff732f9)) ([#3](https://github.com/RowlandOti/E-CommerceApp-Merchant/pull/3))
  - done some code refactor ([32533f6](https://github.com/RowlandOti/E-CommerceApp-Merchant/commit/32533f66d3633f78bf9f0e4a0971f48d70c34756)) ([#3](https://github.com/RowlandOti/E-CommerceApp-Merchant/pull/3))
  - delete old module ([84f1af8](https://github.com/RowlandOti/E-CommerceApp-Merchant/commit/84f1af834813a7afbdad7ade10967dddd79e20d9))
  - refactor and fix: major refactor to hexagonal pattern and fix for loading the images ([b790afc](https://github.com/RowlandOti/E-CommerceApp-Merchant/commit/b790afcccc4442c90dee50274b5702d81b30531c))
  - major refactor to hexagonal pattern ([4c41835](https://github.com/RowlandOti/E-CommerceApp-Merchant/commit/4c4183565cea1adfe7f541a22c0b217d14027dbd))
  - major refactor of code to a Hexagonal architecture of ports and adapters ([03f9bd1](https://github.com/RowlandOti/E-CommerceApp-Merchant/commit/03f9bd1a03eeb153bd0e6afd100d7cac34623f9a))
  - remove domain dependency on the data layer ([4b854e2](https://github.com/RowlandOti/E-CommerceApp-Merchant/commit/4b854e2cfdd9ab9cd9b091d8c545003520047e64))
  - delete old module ([e77599a](https://github.com/RowlandOti/E-CommerceApp-Merchant/commit/e77599a53aaa79a835f42b09e7742d2f1ebcecc5))
  - refactor and fix: major refactor to hexagonal pattern and fix for loading the images ([1889d1f](https://github.com/RowlandOti/E-CommerceApp-Merchant/commit/1889d1ffcc10d488f5a70ad72adedb260cce36f4))
  - major refactor to hexagonal pattern ([e977028](https://github.com/RowlandOti/E-CommerceApp-Merchant/commit/e97702843a948f08fcbd6bf0b17921fd6b7be94c))
  - major refactor of code to a Hexagonal architecture of ports and adapters ([ca0ba3d](https://github.com/RowlandOti/E-CommerceApp-Merchant/commit/ca0ba3d09c83376e0dbe34934f56200c87ed5348))
  - remove domain dependency on the data layer ([0d13b8a](https://github.com/RowlandOti/E-CommerceApp-Merchant/commit/0d13b8a39385ac1f07026fb93c1f888f80f95df5))
  - kotlinifation of some Java files ([55d62f7](https://github.com/RowlandOti/E-CommerceApp-Merchant/commit/55d62f7fd957285480c44389dfaf4efaf94702e6))
  - versioning logic ([a6ab310](https://github.com/RowlandOti/E-CommerceApp-Merchant/commit/a6ab310346dd5854ab583af75b54335a88854501))
  - in code changes ([8fd2d1b](https://github.com/RowlandOti/E-CommerceApp-Merchant/commit/8fd2d1b3cfa74f342109d29f665ea6a22229c5b4))
  - move lambda rguments out of parethesis ([f4d4dec](https://github.com/RowlandOti/E-CommerceApp-Merchant/commit/f4d4decac5452a5e87b84c3357275ce4298fe5b7))
  - rename edit field names ([f6673c3](https://github.com/RowlandOti/E-CommerceApp-Merchant/commit/f6673c34195c6185b7bcc868297b2aba6fbf9dc3))
  - file renames to SplashActivity.kt ([4dd4645](https://github.com/RowlandOti/E-CommerceApp-Merchant/commit/4dd4645c2c9239de2101bad08acd160a880a5a0e))
  - add transition animation ([41d933e](https://github.com/RowlandOti/E-CommerceApp-Merchant/commit/41d933e68c6f01d8891b456779ba1e5dc0f8dc82))
  - convert code to Kotlin ([6fd0677](https://github.com/RowlandOti/E-CommerceApp-Merchant/commit/6fd067721117a8de104b4759ec6eb5e4f85cbe2c))

\* *This CHANGELOG was automatically generated by [auto-generate-changelog](https://github.com/BobAnkh/auto-generate-changelog)*
