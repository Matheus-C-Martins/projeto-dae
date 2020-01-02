<!-- eslint-disable -->
<template>
  <v-card>
    <v-card-title class="headline"> {{ title }} </v-card-title>
    <v-card-text class="pa-0">
      <v-container style="padding-bottom: 0px; padding-top: 0px;">
        <v-row dense>
          <v-col>
            <v-text-field
              v-model='produto.tipo'
              label='Tipo'
              outlined
              dense
              readonly
              disabled>
            </v-text-field>
          </v-col>
          <v-col>
            <v-text-field
              v-model='produto.valorBase'
              label='Valor Base'
              prefix="€"
              outlined
              dense>
            </v-text-field>
          </v-col>
        </v-row>
        <v-row dense>
          <v-col>
            <v-text-field
              v-model='produto.descrição'
              label='Descrição'
              outlined
              dense>
            </v-text-field>
          </v-col>
        </v-row>
      </v-container>
    </v-card-text> 
    <v-card-actions style="padding-top: 0px">
        <v-spacer></v-spacer>
        <v-btn color="blue darken-1" text @click="close">Cancelar</v-btn>
        <v-btn color="blue darken-1" text @click="edit">Editar</v-btn>
    </v-card-actions>
  </v-card>
</template>

<script>
export default {
  /* eslint-disable */
  props: [
    'title',
    'produto'
  ],
  methods: {
    close() {
      this.$emit("close");
    },
    edit () {
      this.$axios.$put(`/api/produtos/${this.produto.tipo}`, {
        descrição: this.produto.descrição,
        valorBase: this.produto.valorBase
      })
      .then(() => {
        this.close()
      })
    }
  }
}
</script>
