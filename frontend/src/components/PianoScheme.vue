<template lang="pug">
  .pianoScheme
    horizontal-scroll.pianoScheme__inner.horizontal-scroll
      .pianoScheme__octave(v-for="octave in [1,2,3,4,5]")
        button.pianoScheme__key C
        button.pianoScheme__key._diez C#
        button.pianoScheme__key D
        button.pianoScheme__key._diez D#
        button.pianoScheme__key E
        button.pianoScheme__key F
        button.pianoScheme__key._diez F#
        button.pianoScheme__key G
        button.pianoScheme__key._diez G#
        button.pianoScheme__key A
        button.pianoScheme__key._diez A#
        button.pianoScheme__key B

</template>

<script>
import HorizontalScroll from 'vue-horizontal-scroll'
import 'vue-horizontal-scroll/dist/vue-horizontal-scroll.css'
export default {
  components: {
    HorizontalScroll
  }
}
</script>

<style scoped lang="sass">
  $width-full-note: 48px
  $width-half-note: 32px
  $half-position: $width-half-note / 2
  .pianoScheme
    position: relative
    -webkit-font-smoothing: antialiased
    -moz-osx-font-smoothing: grayscale
    &__inner
      display: flex
      padding-top: 10px
    &__octave
      position: relative
      display: flex
      &:before
        content: ''
        width: 100%
        height: 10px
        display: block
        background: transparent
        position: absolute
        top: -10px
        left: 0
        flex-shrink: 0
        flex-grow: 1
      &:nth-child(1)
        &:before
          background: #fc5353
      &:nth-child(2)
        &:before
          background: #2df8d0
      &:nth-child(3)
        &:before
          background: #ff7305
      &:nth-child(4)
        &:before
          background: #3537ff
      &:nth-child(5)
        &:before
          background: #55c661

    &__key
      background: $white-color
      border: 1px solid $black-color
      width: $width-full-note
      height: 224px
      transition: $transition
      position: relative
      z-index: 2
      display: flex
      align-items: flex-end
      justify-content: center
      padding: 16px 0
      &:hover
        background: #eeeeee
        cursor: pointer
      &._diez
        background: $black-color
        height: 160px
        color: $white-color
        width: $width-half-note
        position: absolute
        z-index: 3
        align-items: center
        &:hover
          background: #282828
        &:nth-child(2)
          left: $width-full-note * 1 - $half-position
        &:nth-child(4)
          left: $width-full-note * 2 - $half-position
        &:nth-child(7)
          left: $width-full-note * 4 - $half-position
        &:nth-child(9)
          left: $width-full-note * 5 - $half-position
        &:nth-child(11)
          left: $width-full-note * 6 - $half-position
</style>