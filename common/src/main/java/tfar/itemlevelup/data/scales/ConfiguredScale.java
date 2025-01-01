package tfar.itemlevelup.data.scales;

public record ConfiguredScale<SC extends ScaleConfiguration, ST extends ScaleType<SC>>(ST scaleType, SC config)  {
}
