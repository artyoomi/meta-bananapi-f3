# meta-custom-scripts/recipes-core/check-temp/check-temp_1.0.bb
SUMMARY = "Custom script to check CPU temperature"
LICENSE = "MIT"
LIC_FILES_CHKSUM = "file://${COMMON_LICENSE_DIR}/MIT;md5=0835ade698e0bcf8506ecda2f7b4f302"

SRC_URI = "file://thermal.sh"

S = "${WORKDIR}"

do_install() {
    install -d ${D}${bindir}
    install -m 0755 ${S}/thermal.sh ${D}${bindir}/thermal
}

FILES:${PN} = "${bindir}/thermal"
