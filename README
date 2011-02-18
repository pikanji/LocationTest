LocationTest
============
Sample programs demonstrating location sensing on Android.

Versions
--------
Different versions described below can be obtained by specifying the git tag.

### simple
This program demonstrates the usage of LocationManager. It shows that
location update can be requested to multiple location providers, with one
LocationListener.
The listener implemented in this program uses both locations obtained by GPS
and network if they are available. It's purpose is to demonstrate that both
can be used at the same time and with one listener. However, usually it is
practical to use only one of them, since their accuracies are probably
different.
This also shows that the update condition is AND of minimum time interval and
minimum distance change. onLocationChanged is invoked only when the time
elapsed more than then minimum interval and also the distance change is more
than the minimum distance.