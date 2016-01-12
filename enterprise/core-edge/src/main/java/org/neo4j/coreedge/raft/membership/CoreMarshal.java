/*
 * Copyright (c) 2002-2016 "Neo Technology,"
 * Network Engine for Objects in Lund AB [http://neotechnology.com]
 *
 * This file is part of Neo4j.
 *
 * Neo4j is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Affero General Public License as
 * published by the Free Software Foundation, either version 3 of the
 * License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Affero General Public License for more details.
 *
 * You should have received a copy of the GNU Affero General Public License
 * along with this program. If not, see <http://www.gnu.org/licenses/>.
 */
package org.neo4j.coreedge.raft.membership;

import java.nio.ByteBuffer;

import io.netty.buffer.ByteBuf;

import org.neo4j.coreedge.raft.state.membership.Marshal;
import org.neo4j.coreedge.server.AdvertisedSocketAddress;
import org.neo4j.coreedge.server.CoreMember;
import org.neo4j.coreedge.server.AdvertisedSocketAddressDecoder;
import org.neo4j.coreedge.server.AdvertisedSocketAddressEncoder;

/**
 * Format:
 * ┌────────────────────────────────────────────┐
 * │core address ┌─────────────────────────────┐│
 * │             │hostnameLength        4 bytes││
 * │             │hostnameBytes        variable││
 * │             │port                  4 bytes││
 * │             └─────────────────────────────┘│
 * │raft address ┌─────────────────────────────┐│
 * │             │hostnameLength        4 bytes││
 * │             │hostnameBytes        variable││
 * │             │port                  4 bytes││
 * │             └─────────────────────────────┘│
 * └────────────────────────────────────────────┘
 */
public class CoreMarshal implements Marshal<CoreMember>
{
    public void marshal( CoreMember member, ByteBuffer buffer )
    {
        AdvertisedSocketAddressEncoder encoder = new AdvertisedSocketAddressEncoder();
        encoder.encode( member.getCoreAddress(), buffer );
        encoder.encode( member.getRaftAddress(), buffer );
    }

    public void marshal( CoreMember member, ByteBuf buffer )
    {
        AdvertisedSocketAddressEncoder encoder = new AdvertisedSocketAddressEncoder();
        encoder.encode( member.getCoreAddress(), buffer );
        encoder.encode( member.getRaftAddress(), buffer );
    }

    public CoreMember unmarshal( ByteBuffer buffer )
    {
        AdvertisedSocketAddressDecoder decoder = new AdvertisedSocketAddressDecoder();
        AdvertisedSocketAddress coreAddress = decoder.decode( buffer );
        AdvertisedSocketAddress raftAddress = decoder.decode( buffer );
        return new CoreMember( coreAddress, raftAddress );
    }

    public CoreMember unmarshal( ByteBuf buffer )
    {
        AdvertisedSocketAddressDecoder decoder = new AdvertisedSocketAddressDecoder();
        AdvertisedSocketAddress coreAddress = decoder.decode( buffer );
        AdvertisedSocketAddress raftAddress = decoder.decode( buffer );
        return new CoreMember( coreAddress, raftAddress );
    }
}