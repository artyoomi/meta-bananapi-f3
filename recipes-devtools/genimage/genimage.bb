SUMMARY = "Tool to generate multiple filesystem and flash images from a tree"
HOMEPAGE = "https://github.com/pengutronix/genimage"
SECTION = "devel"
LICENSE = "GPLv2"
LIC_FILES_CHKSUM = "file://COPYING;md5=570a9b3749dd0463a1778803b12a6dce"

DEPENDS += "autoconf-native automake-native confuse-native"

SRC_URI = "git://github.com/pengutronix/genimage.git;protocol=https;branch=master"
SRCREV = "${AUTOREV}"

S = "${WORKDIR}/git"

# Since we're looking to use this as a host tool, we need to ensure it's installed in the host environment
inherit autotools pkgconfig

do_install() {
    # Install the genimage binary to the host tools directory
    install -d ${D}${bindir}
    install -m 0755 ${B}/genimage ${D}${bindir}/
}

# We want to install this tool to the host system, not to the target filesystem
BBCLASSEXTEND = "native nativesdk"
