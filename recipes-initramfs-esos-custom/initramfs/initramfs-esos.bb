SUMMARY = "The custom initramfs with esos"
DESCRIPTION = "The script corrects the sound due to problems with initramfs"
LICENSE = "MIT"
LIC_FILES_CHKSUM = "file://${COMMON_LICENSE_DIR}/MIT;md5=0835ade698e0bcf8506ecda2f7b4f302"

SRC_URI = "file://initramfs-esos-custom.img"

S = "${WORKDIR}"

do_install() {
    install -d ${D}/usr/bin
    install -m 0755 ${WORKDIR}/therm.sh ${D}/usr/bin/therm
}

do_deploy() {
        install -d ${DEPLOYDIR}/images
        install -m 0755 ${WORKDIR}/initramfs-esos-custom.img ${DEPLOYDIR}/images/initramfs-generic.img
}

