# Add config file
FILESEXTRAPATHS:prepend := "${THISDIR}/${PN}:"
SRC_URI:append := "file://busybox.cfg"
