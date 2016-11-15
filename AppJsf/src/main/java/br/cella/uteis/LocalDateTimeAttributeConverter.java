// Classe LocalDateTimeAttributeConverter com os métodos conversores de datas
package br.cella.uteis;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

/**
 * Essa classe serve para converter o valor de LocalDateTime para timestamp
 * 
 * @author Willian Cella - 14 de nov de 2016 - 22:15:28
 */

@Converter(autoApply = true)
public class LocalDateTimeAttributeConverter implements AttributeConverter<LocalDateTime, Timestamp> {

	/*
	 * Converte o LocalDateTime em timestamp para poder persistie no banco de
	 * dados
	 */
	@Override
	public Timestamp convertToDatabaseColumn(LocalDateTime localDateTime) {
		if (localDateTime != null)
			return Timestamp.valueOf(localDateTime);
		return null;
	}

	/*
	 * Quando retornar a data do banco de dados, vai estar como timestamp Aqui
	 * vai ser convertido para LocalDateTime para a entidade
	 */
	@Override
	public LocalDateTime convertToEntityAttribute(Timestamp timestamp) {
		if (timestamp != null)
			return timestamp.toLocalDateTime();
		return null;
	}
}
