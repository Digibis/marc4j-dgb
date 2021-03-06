// $Id: RecordBuilder.java,v 1.5 2003/05/08 16:30:48 ceyates Exp $
/**
 * Copyright (C) 2002 Bas Peters
 *
 * This file is part of MARC4J
 *
 * MARC4J is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation; either
 * version 2.1 of the License, or (at your option) any later version.
 *
 * MARC4J is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with MARC4J; if not, write to the Free Software
 * Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA 02111-1307 USA
 */
package org.marc4j.helpers;

import org.marc4j.MarcHandler;
import org.marc4j.marc.ControlField;
import org.marc4j.marc.DataField;
import org.marc4j.marc.Leader;
import org.marc4j.marc.Record;
import org.marc4j.marc.Subfield;

/**
 * <p>
 * Creates record objects from <code>MarcHandler</code> events and reports
 * events to the <code>RecordHandler</code>.
 * </p>
 *
 * @author <a href="mailto:mail@bpeters.com">Bas Peters</a>
 * @version $Revision: 1.5 $
 *
 * @see RecordHandler
 */
public class RecordBuilder
    implements MarcHandler
{

    /** The RecordHandler object. */
    private RecordHandler recordHandler;

    /** Record object */
    private Record record;

    /** Data field object */
    private DataField datafield;

    /**
     * <p>
     * Registers the <code>RecordHandler</code> object.
     * </p>
     *
     * @param recordHandler the record handler object
     */
    public void setRecordHandler(RecordHandler recordHandler)
    {
        this.recordHandler = recordHandler;
    }

    /**
     * <p>
     * Reports the start of the file.
     * </p>
     */
    public void startCollection()
    {
        if (recordHandler != null) recordHandler.startCollection();
    }

    /**
     * <p>
     * Creates a new record object.
     * </p>
     */
    public void startRecord(Leader leader)
    {
        this.record = new Record();
        record.add(leader);
    }

    /**
     * <p>
     * Adds a control field to the record object.
     * </p>
     */
    @Override
    public void controlField(String tag, char[] data, Long id)
    {
        record.add(new ControlField(tag, data, id));
    }

    /**
     * <p>
     * Creates a new data field object.
     * </p>
     */
    @Override
    public void startDataField(String tag, char ind1, char ind2, Long id)
    {
        datafield = new DataField(tag, ind1, ind2, id);
    }

    /**
     * <p>
     * Adds a subfield to the data field.
     * </p>
     */
    @Override
    public void subfield(char identifier, char[] data, String linkCode)
    {
        datafield.add(new Subfield(identifier, data, linkCode));
    }

    /**
     * <p>
     * Adds a data field to the record object.
     * </p>
     */
    public void endDataField(String tag)
    {
        record.add(datafield);
    }

    /**
     * <p>
     * Reports the end of a record and sets the record object.
     * </p>
     */
    public void endRecord()
    {
        if (recordHandler != null) recordHandler.record(record);
    }

    /**
     * <p>
     * Reports the end of the file.
     * </p>
     */
    public void endCollection()
    {
        if (recordHandler != null) recordHandler.endCollection();
    }

}
