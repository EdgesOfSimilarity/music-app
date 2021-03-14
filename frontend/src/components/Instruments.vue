<template lang="pug">
  .instruments.columns
    .instruments__category.column(v-for="instrument in instrumentsPrepare")
      .instrumentsItem
        .card
          .card-image
            figure.image.is-4by3
              img.instrumentsItem__img(:src="'/img/instrumentsPreview/' + instrument.img" alt='Placeholder image')
          .card-content
            .media
              .media-content
                p.title.is-4 {{ instrument.title }}



        // img.instrumentsItem__img(:src="'/img/instrumentsPreview/' + instrument.img")
</template>

<script>
import InstrumentItem from "@/components/InstrumentItem";
export default {
  components: {
    InstrumentItem
  },
  data() {
    return {
      instruments: [
        { name: 'guitar' },
        { name: 'ukulele' },
        { name: 'pianoMidi' },
        { name: 'drumMidi' }
      ]
    };
  },
  props: {
    data: {
      type: Array,
      required: false
    }
  },
  computed: {
    instrumentsPrepare() {
      const result = [];
      this.instruments.forEach(instrument => {
        let title = '';
        switch (instrument.name) {
          case 'guitar':
            title = 'Гитара';
            break;
          case 'ukulele':
            title = 'Укулеле';
            break;
          case 'pianoMidi':
            title = 'Фортепиано';
            break;
          case 'drumMidi':
            title = 'Дрампад';
            break;
        }

        result.push({
          name: instrument.name,
          img: `${instrument.name}.png`,
          title: title
        });
      });
      return result;
    }
  }
};
</script>

<style scoped lang="sass">
.instrumentsItem
  width: 300px
  &__img
    object-fit: contain
</style>
