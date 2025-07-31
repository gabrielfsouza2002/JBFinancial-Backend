-- src/main/resources/db/migration/V7__trigger_negative_base.sql

-- Criação da função que será chamada pelo trigger
CREATE OR REPLACE FUNCTION enforce_negative_value()
RETURNS TRIGGER AS $$
BEGIN
    IF NEW.debt_cred = false AND NEW.valor > 0 THEN
        NEW.valor := NEW.valor * -1;
END IF;
RETURN NEW;
END;
$$ LANGUAGE plpgsql;

-- Criação do trigger que chama a função antes de cada inserção ou atualização
CREATE TRIGGER enforce_negative_value_trigger
    BEFORE INSERT OR UPDATE ON base
                         FOR EACH ROW
                         EXECUTE FUNCTION enforce_negative_value();