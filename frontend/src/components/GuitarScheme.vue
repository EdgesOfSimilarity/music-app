<template lang="pug">
  .guitarScheme
    .guitarScheme__strings(v-for="string in [1,2,3,4,5,6]")
      .guitarScheme__string
        .guitarScheme__fret(v-for="fret in [0, 1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,16,17,18,19,20,21,22,23,24]")
          .guitarScheme__note X
    .guitarScheme__frets
      .guitarScheme__fret(v-for="fret in [0,1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,16,17,18,19,20,21,22,23,24]")
        .guitarScheme__nums {{ fret === 0 ? '' : fret }}
</template>

<script>
export default {}
</script>

<style scoped lang="sass">
.guitarScheme
  width: 100%
  &__string
    display: flex
    position: relative
    height: 32px
    margin-bottom: 8px
    &:before
      content: ''
      position: absolute
      top: 50%
      transform: translateY(-50%)
      left: 80px
      width: 100%
      height: 2px
      background: #000000
  &__fret
    display: flex
    justify-content: center
    width: 64px
    &:first-child
      & .guitarScheme__note
        background: #ffec90
  &__note
    width: 32px
    height: 32px
    background: #8f9aff
    border-radius: 50%
    position: relative
    display: flex
    justify-content: center
    align-items: center
    z-index: 2
  &__frets
    display: flex
  &__nums
    width: 32px
    display: flex
    justify-content: center
</style>