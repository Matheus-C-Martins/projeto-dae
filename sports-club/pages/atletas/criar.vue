<!-- eslint-disable -->
<template>
  <v-card>
    <v-card-title class="headline">{{ title }}</v-card-title>
    <v-card-text class="pa-0">
      <v-container style="padding-bottom: 0px; padding-top: 0px;">
        <v-row dense>
          <v-col>
            <v-text-field
              v-model="atleta.username"
              label="Username"
              :error-messages="usernameErrors"
              outlined
              dense
              @input="$v.atleta.username.$touch()"
              @blur="$v.atleta.username.$touch()"
            ></v-text-field>
          </v-col>
          <v-col>
            <v-text-field
              v-model="atleta.nome"
              label="Nome"
              :error-messages="nomeErrors"
              outlined
              dense
              @input="$v.atleta.nome.$touch()"
              @blur="$v.atleta.nome.$touch()"
            ></v-text-field>
          </v-col>
        </v-row>
        <v-row dense>
          <v-col>
            <v-text-field
              v-model="atleta.email"
              label="Email Address"
              :error-messages="emailErrors"
              outlined
              dense
              required
              @input="$v.atleta.email.$touch()"
              @blur="$v.atleta.email.$touch()"
            ></v-text-field>
          </v-col>
          <v-col>
            <v-text-field
              v-model="atleta.password"
              :value="atleta.password"
              label="Password"
              :append-icon="value ? 'mdi-eye' : 'mdi-eye-off'"
              @click:append="() => (value = !value)"
              :type="value ? 'password' : 'text'"
              :error-messages="passwordErrors"
              outlined
              dense
              @input="$v.atleta.password.$touch()"
              @blur="$v.atleta.password.$touch()">
              </v-text-field>
          </v-col>
        </v-row>
        <v-row dense>
          <v-col>
            <v-select
            v-model="modalidade"
            :items="modalidades"
            label="Modalidade"
            outlined
            dense
            :error-messages="modalidadeErrors"
            @input="$v.modalidade.$touch()"
            @blur="$v.modalidade.$touch()">
            <template slot="selection" slot-scope="data">
              {{ data.item.nome }} {{ data.item.escalao }}
            </template>
            <template slot="item" slot-scope="data">
              {{ data.item.nome }} {{ data.item.escalao }}
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
  props: ["title", "atleta"],
  mixins: [validationMixin],
  validations: {
    atleta: {
      nome: { required, maxLength: maxLength(25) },
      username: { required, maxLength: maxLength(10) },
      email: { required, email },
      password: { required, minLength: minLength(3) }
    },
    modalidade: { required }
  },
  data () {
    return {
      valid: true,
      value: true,
      modalidade: {},
      modalidades: []
    }
  },
  computed: {
    nomeErrors () {
      const errors = []
      if (!this.$v.atleta.nome.$dirty) { return errors }
      !this.$v.atleta.nome.maxLength && errors.push('Name must be at most 25 characters long.')
      !this.$v.atleta.nome.required && errors.push('Name is required.')
      return errors
    },
    usernameErrors () {
      const errors = []
      if (!this.$v.atleta.username.$dirty) { return errors }
      !this.$v.atleta.username.maxLength && errors.push('Username must be at most 10 characters long.')
      !this.$v.atleta.username.required && errors.push('Username is required.')
      return errors
    },
    passwordErrors () {
      const errors = []
      if (!this.$v.atleta.password.$dirty) { return errors }
      !this.$v.atleta.password.minLength && errors.push('Password must have a minimum of 3 characters.')
      !this.$v.atleta.password.required && errors.push('Password is required.')
      return errors
    },
    emailErrors () {
      const errors = []
      if (!this.$v.atleta.email.$dirty) { return errors }
      !this.$v.atleta.email.email && errors.push('Must be valid e-mail.')
      !this.$v.atleta.email.required && errors.push('E-mail is required.')
      return errors
    },
    modalidadeErrors() {
      const errors = []
      if (!this.$v.modalidade.$dirty) { return errors }
      !this.$v.modalidade.required && errors.push('Modalidade is required.')
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
      this.$axios.$post('/api/atletas', {
        username: this.atleta.username,
        password: this.atleta.password,
        nome: this.atleta.nome,
        email: this.atleta.email
      })
      .then(() => {
        this.$axios.$post(`/api/atletas/${this.atleta.username}/modalidades`, {
          nome : this.modalidade.nome,
          escalao : this.modalidade.escalao
        })
        .then(() => {
          this.close()
        })
      })
    },
    getModalidades() {
      this.$axios.$get('/api/modalidades').then(response => {
        this.modalidades = response;
      })
      .catch(response => {
        console.log(`ERROR: ${response.data.data}`);
      });
    }
  }
};
</script>
