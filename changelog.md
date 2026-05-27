# Changelog

## [1.0.1] - 2026-05-27

### Fixed
- Fixed visual rendering and face occlusion bugs when looking at or walking through an inactive (toggled) **John Rock** block.
  - Added `.noOcclusion()` to the block properties definition.
  - Registered `john_rock` to the `translucent` render layer in `testosteroneClient`.
