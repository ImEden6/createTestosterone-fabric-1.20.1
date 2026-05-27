# Changelog

## [1.1.0] - 2026-05-28

### Added
- **EMI Compatibility**: Implemented direct EMI recipe viewer support inside the main mod.
  - Registered the custom `testosterone:decantation` recipe category.
  - Configured the Decanter Centrifuge block as the workstation catalyst for the category.
  - Created `DecantationEmiRecipe` displaying fluid inputs, fluid outputs, and a decorative Decanter Centrifuge block icon in the recipe GUI.
  - Declared the client-side EMI plugin entrypoint (`TestosteroneEmiPlugin`) in `fabric.mod.json`.

## [1.0.1] - 2026-05-27

### Added
- **Dynamic Fluid Recipe Scaling**: Added `RecipeManagerMixin` to intercept `RecipeManager.fromJson` loading of all recipes under the `testosterone` namespace, dynamically multiplying fluid `amount` fields by `81` to convert standard millibuckets (mB) to droplets.
- **Centrifuge Processing Feedback**: Added brewing stand sound effects (`SoundEvents.BREWING_STAND_BREW`) and bubble particles (`ParticleTypes.BUBBLE`) during decantation ticks inside `decanterCentrifugeBlockEntity`.
- **Centrifuge Block Updates**: Added `setChanged()` and `sendData()` calls in `decanterCentrifugeBlockEntity` upon matching recipes to enforce server-client synchronization.

### Fixed
- **Centrifuge Double Scaling**: Modified `decantation.java` processing math to match recipes using the already-scaled droplet inputs, removing redundant scaling math.
- **Tipped Arrow Potion Keys**: Corrected potion reference keys in `testosterone_tipped_arrow.json` and `trenbolone_tipped_arrow.json` recipes to map to registered potion IDs (`man_power_potion` and `roid_rage_potion`).
- **Fragile Copycat rendering**: Configured cutout render layers (`BlockRenderLayerMap`) and added `.noOcclusion()` block behavior properties to fragile copycat blocks to prevent occlusion and rendering glitches.
- **Fragile Copycat parent model**: Restructured `fragile_copycat_block.json` to point to the correct parent asset path `testosterone:block/fragile_copycat/0`.
