<!-- eslint-disable -->
<template>
  <v-card>
    <v-card-title class="headline">{{ title }}</v-card-title>
    <v-card-text class="pa-0">
      <v-container style="padding-bottom: 0px; padding-top: 0px;">
        <v-row dense>
          <v-col>
            <v-text-field
              v-model="treinador.username"
              label="Username"
              :error-messages="usernameErrors"
              outlined
              dense
              @input="$v.treinador.username.$touch()"
              @blur="$v.treinador.username.$touch()"
            ></v-text-field>
          </v-col>
          <v-col>
            <v-text-field
              v-model="treinador.nome"
              label="Nome"
              :error-messages="nomeErrors"
              outlined
              dense
              @input="$v.treinador.nome.$touch()"
              @blur="$v.treinador.nome.$touch()"
            ></v-text-field>
          </v-col>
        </v-row>
        <v-row dense>
          <v-col>
            <v-text-field
              v-model="treinador.email"
              label="Email Address"
              :error-messages="emailErrors"
              outlined
              dense
              required
              @input="$v.treinador.email.$touch()"
              @blur="$v.treinador.email.$touch()"
            ></v-text-field>
          </v-col>
          <v-col>
            <v-text-field
              v-model="treinador.password"
              :value="treinador.password"
              label="Password"
              :append-icon="value ? 'mdi-eye' : 'mdi-eye-off'"
              @click:append="() => (value = !value)"
              :type="value ? 'password' : 'text'"
              :error-messages="passwordErrors"
              outlined
              dense
              @input="$v.treinador.password.$touch()"
              @blur="$v.treinador.password.$touch()">
              </v-text-field>
          </v-col>
        </v-row>
        <v-row dense>
          <v-col>
            <v-select
            v-model="modalidade"
            :items="modalidades"
            label="Modalidade"
            @change="getModalidadeEscaloes()"
            outlined
            dense
            :error-messages="modalidadeErrors"
            @input="$v.modalidade.$touch()"
            @blur="$v.modalidade.$touch()"
            no-data-text="Não existem modalidades disponíveis para inscrição.">
            <template slot="selection" slot-scope="data">
              {{ data.item.nome }}
            </template>
            <template slot="item" slot-scope="data">
              {{ data.item.nome }}
            </template>
            </v-select>
          </v-col>
          <v-col>
            <v-select
            v-model="escalao"
            :items="escaloes"
            label="Escalao"
            outlined
            dense
            :error-messages="escalaoErrors"
            @input="$v.escalao.$touch()"
            @blur="$v.escalao.$touch()"
            no-data-text="Escolha uma modalidade primeiro.">
            <template slot="selection" slot-scope="data">
              {{ data.item.escalao }}
            </template>
            <template slot="item" slot-scope="data">
              {{ data.item.escalao }}
            </template>
            </v-select>
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
import { required, maxLength, minLength, email } from 'vuelidate/lib/validators'

export default {
  props: ["title", "treinador"],
  mixins: [validationMixin],
  validations: {
    treinador: {
      nome: { required, maxLength: maxLength(25) },
      username: { required, maxLength: maxLength(10) },
      email: { required, email },
      password: { required, minLength: minLength(3) }
    },
    modalidade: { required },
    escalao: { required }
  },
  data () {
    return {
      valid: true,
      value: true,
      modalidade: {},
      modalidades: [],
      escalao: {},
      escaloes: []
    }
  },
 computed: {
    nomeErrors () {
      const errors = []
      if (!this.$v.treinador.nome.$dirty) { return errors }
      !this.$v.treinador.nome.maxLength && errors.push('Nome deve ter no máximo 25 carateres.')
      !this.$v.treinador.nome.required && errors.push('Name é obtigatório.')
      return errors
    },
    usernameErrors () {
      const errors = []
      if (!this.$v.treinador.username.$dirty) { return errors }
      !this.$v.treinador.username.maxLength && errors.push('Username deve ter no máximo 10 carateres.')
      !this.$v.treinador.username.required && errors.push('Username é obtigatório.')
      return errors
    },
    passwordErrors () {
      const errors = []
      if (!this.$v.treinador.password.$dirty) { return errors }
      !this.$v.treinador.password.minLength && errors.push('Password deve ter no mínimo 3 carateres.')
      !this.$v.treinador.password.required && errors.push('Password é obtigatória.')
      return errors
    },
    emailErrors () {
      const errors = []
      if (!this.$v.treinador.email.$dirty) { return errors }
      !this.$v.treinador.email.email && errors.push('Insira um email válido.')
      !this.$v.treinador.email.required && errors.push('E-mail é obtigatório.')
      return errors
    },
    modalidadeErrors() {
      const errors = []
      if (!this.$v.modalidade.$dirty) { return errors }
      !this.$v.modalidade.required && errors.push('Modalidade é obtigatória.')
      return errors
    },
    escalaoErrors() {
      const errors = []
      if (!this.$v.escalao.$dirty) { return errors }
      !this.$v.escalao.required && errors.push('Escalao é obtigatório.')
      return errors
    }
  },
  mounted() {
    this.getModalidades();
  },
  methods: {
    close() {
      this.$emit("close");
    },
    save() {
      this.$v.$touch()
      if (this.$v.$error) {
        return
      }
      this.$axios.$post('/api/treinadores', {
        username: this.treinador.username,
        password: this.treinador.password,
        nome: this.treinador.nome,
        email: this.treinador.email
      })
      .then(() => {
        this.$axios.$post(`/api/treinadores/${this.treinador.username}/modalidades`, {
          nome : this.modalidade.nome,
          escalao : this.escalao.escalao
        })
        .then(() => {
          this.close()
        })
      })
    },
    getModalidades() {
      this.$axios.$get('/api/modalidades').then((modalidades) => {
        this.modalidades = modalidades
      });
    },
    getModalidadeEscaloes() {
      this.$axios.$get(`/api/modalidades/${this.modalidade.nome}`).then((escaloes) => {
        this.escaloes = escaloes
      });
    }
  }
};
</script>
