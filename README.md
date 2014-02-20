Sample: Event object binding with Jackson
==========================================

* Author: rinrinne a.k.a. rin_ne
* Repository: https://github.com/rinrinne/bind-event-sample

Synopsis
---------------------------------

This is a sample for binding JSON to gerrit event objects using [Jackson].

Some event and data classes in gerrit are imported into event-model module
with a bit modification.

Contents in event-model
----------------------------------

* Copy [event] and [data] class from Gerrit repository
* Remove unnecessary classes
  * events/EventFactory.java
  * events/CommitReceivedEvent.java
* Copy some classes from [reviewdb] then modify them to use enum only.
  * Patch.java (Patch.ChangeType)
  * Change.java (Change.Status)
  * CodedEnum.java (interface)

Environments
----------------------------------

* `linux`
  * `java-1.6`
    * `maven-3.0.4`

Build
----------------------------------

    mvn clean install
    cd bind-event
    mvn exec:java

License
----------------------------------

The Apache Software License, Version 2.0

Copyright
---------------------------------

Copyright (c) 2014 rinrinne a.k.a. rin_ne


[Jackson]: http://jackson.codehaus.org/
[event]: https://gerrit.googlesource.com/gerrit/+/master/gerrit-server/src/main/java/com/google/gerrit/server/events/
[data]: https://gerrit.googlesource.com/gerrit/+/master/gerrit-server/src/main/java/com/google/gerrit/server/data/
[reviewdb]: https://gerrit.googlesource.com/gerrit/+/master/gerrit-reviewdb/src/main/java/com/google/gerrit/reviewdb/client/
