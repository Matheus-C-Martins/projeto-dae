<!-- eslint-disable -->
<template>
  <v-card>
    <v-card-title class="headline"> Adicionar Treinador </v-card-title>
    <v-card-text style="padding-bottom: 0px">
      <v-row dense>
        <v-col>
          <v-select
            v-model="treinador"
            :items="treinadores"
            label="Treinadores"
            :error-messages="treinadorErrors"
            outlined
            dense
            @input="$v.treinador.$touch()"
            @blur="$v.treinador.$touch()"
            no-data-text="Não existem treinadores disponíveis para inscrição.">
            <template slot="selection" slot-scope="data">
              {{ data.item.username }} - {{ data.item.nome}}
            </template>
            <template slot="item" slot-scope="data">
              {{ data.item.username }} - {{ data.item.nome}}
            </template>
          </v-select>
        </v-col>
      </v-row>
    </v-card-text>
    <v-card-actions style="padding-top: 0px">
      <v-spacer></v-spacer>
      <v-btn color="blue darken-1" text @click="close"> Cancelar </v-btn>
      <v-btn color="blue darken-1" text @click="inscrever"> Inscrever </v-btn>
    </v-card-actions>
  </v-card>
</template>

<script>
/* eslint-disable */
import { validationMixin } from 'vuelidate'
import { required } from 'vuelidate/lib/validators'

export default {
  props: [ 'nome', 'escalao' ],
  mixins: [validationMixin],
  validations: { treinador: { required } },
  data () {
    return {
      treinador: {},
      treinadores: []
    }
  },
  created () {
    this.$axios.$get('/api/treinadores').then((treinadores) => {
      this.treinadores = treinadores;
    })
  },
  computed: {
    treinadorErrors () {
      const errors = []
      if (!this.$v.treinador.$dirty) { return errors }
      !this.$v.treinador.required && errors.push('Treinador é obrigatório.')
      return errors
    }
  },
  methods: {
    close() {
      this.$emit("close");
    },
    inscrever() {
      this.$v.$touch()
      if (this.$v.$error) {
        return
      }
      this.$axios.$post(`/api/treinadores/${this.treinador.username}/modalidades`, {
        nome : this.nome,
        escalao : this.escalao
      })
      .then(() => {
        this.close()
      })
    },
  }
}
</script>
