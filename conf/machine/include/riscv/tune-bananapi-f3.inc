require conf/machine/include/riscv/tune-riscv.inc

TUNEVALID[bananapi-f3-series] = "BananaPi F3 Cores"

AVAILTUNES += " bananapi-f3-series"

# BananaPi F3 Series Cores
TUNE_FEATURES:tune-bananapi-f3-series = "${TUNE_FEATURES:tune-riscv64} bananapi-f3-series"
TUNE_ARCH:tune-bananapi-f3-series = "riscv64"
TUNE_PKGARCH:tune-bananapi-f3-series = "riscv64"
PACKAGE_EXTRA_ARCHS:tune-bananapi-f3-series = "riscv64"

# TODO: Need this?
#TUNE_CCARGS:append = "${@bb.utils.contains('TUNE_FEATURES', 'bananapi-f3-series', ' -mtune=bananapi-f3-series', ' ', d)}"
