<!-- eslint-disable -->
<template>
  <v-app>
    <v-container fluid>
      <v-card>
        <v-card-title class="headline"> Enviar Email ao Sócio: {{ socio.nome }} </v-card-title>
        <v-card-text>
          <v-form>
            <v-row align="center" justify="center">
              <v-text-field
                v-model="assunto"
                label="Assunto"
                outlined
                dense>
              </v-text-field>
            </v-row>
            <v-row align="center" justify="center">
              <v-textarea
                v-model="mensagem"
                label="Mensagem"
                outlined
                dense>
              </v-textarea>
            </v-row>
          </v-form>
        </v-card-text>
        <v-card-actions>
          <v-spacer></v-spacer>
          <v-btn @click="back()"> Voltar </v-btn>
          <v-btn color="primary" dark @click="send()"> Enviar </v-btn>
        </v-card-actions>
      </v-card>
    </v-container>
  </v-app>
</template>

<script>
/* eslint-disable */
export default {
  data() {
    return {
      socio: {},
      assunto: null,
      mensagem: null
    };
  },
  created() {
    this.$axios.$get(`/api/socios/${this.username}`).then(socio => {
      this.socio = socio;
    });
  },
  computed: {
    username() {
      return this.$route.params.username;
    },
    nome() {
      return this.$route.params.nome;
    },
    escalao() {
      return this.$route.params.escalao;
    }
  },
  methods: {
    back() {
      this.$router.push(`/modalidades/${this.nome}/${this.escalao}`);
    },
    send() {
      this.$axios.$post(`/api/socios/${this.username}/email/send`, {
        assunto: this.assunto,
        mensagem: this.mensagem
      })
      .then(msg => {
        this.$toast.success(msg);
        this.back();
      })
      .catch(error => {
        this.$toast.error("Erro a enviar e-mail"+ error);
      });
    }
  }
};
</script>
