pdfviewer-sdk
=============

Mavenized project for pdfviewer sdk from http://www.androidpdf.mobi/.

This library does not use any automated build tool, hence making it difficult to integrate. The original solution is to copy the resources and reference them locally.
I will try to keep it up-to-date with new releases.



## Disclaimer
* I **do not mantain** this library. Just mavenized the project to use it as an apklib, and published it to a public repo so that anyone can use it and/or deploy it to their Nexus repository.
* FAQs or any other question library related, please refer to their forum: http://www.androidpdf.mobi/forum.
* The issue tracker can be found here: http://www.androidpdf.mobi/support/mantis.

## Installation and Deployment

To install to your local Maven repository:
 `$ mvn clean install`

To deploy to your remote Maven repository:
 `$ mvn deploy -Drepo.id=my-repo-id -Drepo.name=my-repo-name -Drepo.url=my-repo-url`

## Use dependency
```xml
<dependency>
  <groupId>com.radaee.reader</groupId>
  <artifactId>pdfviewer</artifactId>
  <version>{version}</version>
  <type>apklib</type>
</dependency>
```

Where `{version}` is the the tag name.

## Enhacements to Global class
I made some tweaks to the `Global` class -used to initialize the library- regarding license authentication. You now can use an Enum to specify the `LicenseType` and parameterize the authentication values. For example:

```java
Global.licenseType = Global.LicenseType.STANDARD;
Global.company = "Zauber";
Global.key = "123456789FOO";
Global.email = "matias@zauberlabs.com";
```

If not specified, `Global` uses `LicenseType.STANDARD` with defaults values:

```java
/**
 * License Authentication
 */
public static enum LicenseType {
    STANDARD, PROFESSIONAL, PREMIUM
};
public static LicenseType licenseType = LicenseType.STANDARD;

public static String company = "radaee";
public static String email = "radaee_com@yahoo.cn";
public static String key = "HV8A19-WOT9YC-9ZOU9E-OQ31K2-FADG6Z-XEBCAO";
```

## License
Provided by the sdk: https://github.com/mdumrauf/pdfviewer-sdk/blob/master/LICENSE.pdf
