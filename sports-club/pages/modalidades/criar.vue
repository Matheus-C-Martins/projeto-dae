<!-- eslint-disable -->
<template>
  <v-card>
    <v-card-title class="headline"> Criar Modalidade </v-card-title>
    <v-card-text class="pa-0">
      <v-container style="padding-bottom: 0px; padding-top: 0px;">
        <v-row dense>
          <v-col>
            <v-text-field
              v-model="modalidade.nome"
              label="Nome"
              :error-messages="nomeErrors"
              outlined
              dense
              @input="$v.modalidade.nome.$touch()"
              @blur="$v.modalidade.nome.$touch()"
            ></v-text-field>
          </v-col>
          <v-col>
            <v-text-field
              v-model="modalidade.escalao"
              label="Escalão"
              :error-messages="escalaoErrors"
              outlined
              dense
              @input="$v.modalidade.escalao.$touch()"
              @blur="$v.modalidade.escalao.$touch()"
            ></v-text-field>
          </v-col>
        </v-row>
      </v-container>
    </v-card-text>
    <v-card-actions style="padding-top: 0px">
      <v-spacer></v-spacer>
      <v-btn color="blue darken-1" text @click="close"> Cancelar </v-btn>
      <v-btn color="blue darken-1" text @click="save"> Criar </v-btn>
    </v-card-actions>
  </v-card>
</template>

<script>
/* eslint-disable */
import { validationMixin } from 'vuelidate'
import { required, maxLength, minLength, helpers } from 'vuelidate/lib/validators'
export const escalaoRegex = helpers.regex('valueRegex', /^(Sub-[0-2]\d)$/i)

export default {
  props: [ 'modalidade' ],
  mixins: [validationMixin],
  validations: {
    modalidade: {
      nome: { required, maxLength: maxLength(25) },
      escalao: { required, maxLength: maxLength(6), minLength: minLength(6), escalaoRegex }
    }
  },
  computed: {
    nomeErrors () {
      const errors = []
      if (!this.$v.modalidade.nome.$dirty) { return errors }
      !this.$v.modalidade.nome.maxLength && errors.push('Nome só pode ter 25 carateres, no máximo.')
      !this.$v.modalidade.nome.required && errors.push('Nome é necessário.')
      return errors
    },
    escalaoErrors () {
      const errors = []
      if (!this.$v.modalidade.escalao.$dirty) { return errors }
      !this.$v.modalidade.escalao.maxLength && errors.push('Escalão deve ser escrito no formato: "Sub-XX".')
      !this.$v.modalidade.escalao.minLength && errors.push('Escalão deve ser escrito no formato: "Sub-XX".')
      !this.$v.modalidade.escalao.escalaoRegex && errors.push('Escalão deve ser escrito no formato: "Sub-XX".')
      !this.$v.modalidade.escalao.required && errors.push('Escalão é necessário.')
      return errors
    }
  },
  methods: {
    close() {
      this.$emit("close");
    },
    save () {
      this.$v.$touch()
      if (this.$v.$error) {
        return
      }
      this.$axios.$post('/api/modalidades', {
        nome: this.modalidade.nome,
        escalao: this.modalidade.escalao
      })
      .then(() => {
        this.close()
      })
    }
  }
};
</script>
